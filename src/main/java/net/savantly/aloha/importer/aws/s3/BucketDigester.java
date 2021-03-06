package net.savantly.aloha.importer.aws.s3;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StreamUtils;

import net.savantly.aloha.importer.aws.AwsConfigProperties;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.DbfImporter;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;
import net.savantly.aloha.importer.dbf.ImportProcessingRequest;
import net.savantly.aloha.importer.dbf.ImporterBeanResolver;
import net.savantly.aloha.importer.domain.importedFiles.ImportState;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.stream.BreakingForEach;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request.Builder;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

public class BucketDigester {

	public final static String BEAN_NAME = "bucketDigester";

	private final static Logger log = LoggerFactory.getLogger(BucketDigester.class);

	private final ImporterBeanResolver importerResolver;
	private final AwsConfigProperties props;
	private final S3Client s3;
	private final ResourceLoader resourceLoader;
	private final DataSource datasource;
	private final S3FilenameUtil s3FileNameUtil;

	final AtomicInteger eligibleCountTotal = new AtomicInteger(0);
	final AtomicInteger processedCountTotal = new AtomicInteger(0);
	final AtomicInteger skippedCountTotal = new AtomicInteger(0);
	final AtomicInteger erroredCountTotal = new AtomicInteger(0);
	private final AtomicBoolean stop = new AtomicBoolean(false);
	private final Lock lock = new ReentrantLock();

	public BucketDigester(AwsConfigProperties props, S3Client s3, ImporterBeanResolver importerResolver,
			ResourceLoader resourceLoader, DataSource datasource, S3FilenameUtil s3FileNameUtil) {
		this.props = props;
		this.s3 = s3;
		this.importerResolver = importerResolver;
		this.resourceLoader = resourceLoader;
		this.datasource = datasource;
		this.s3FileNameUtil = s3FileNameUtil;

	}

	public void stopDigest() {
		stop.set(true);
	}

	public String getStatus() {
		boolean isRunning = false;
		if (lock.tryLock()) {
			lock.unlock();
		} else {
			isRunning = true;
		}
		return String.format("running: %s, eligible: %s processed: %s skipped: %s errored: %s", isRunning,
				eligibleCountTotal.get(), processedCountTotal.get(), skippedCountTotal.get(), erroredCountTotal.get());
	}

	protected void digest(LocalDate startDate, LocalDate endDate, String prefixTemplate, String dateFormat,
			List<String> posList) {
		DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder().appendPattern(dateFormat).toFormatter();
		BreakingForEach.forEach(posList.stream(), (posKey, posListBreaker) -> {
			if (stop.get()) {
				posListBreaker.stop();
			} else {
				final Stream<LocalDate> dates = startDate.datesUntil(endDate);
				BreakingForEach.forEach(dates, (date, dateBreaker) -> {
					if (stop.get()) {
						dateBreaker.stop();
					} else {
						String prefix = prefixTemplate.replace("{posKey}", posKey).replace("{date}",
								date.format(dateFormatter));
						log.debug("digesting prefix: {}", prefix);
						digest(prefix);
					}
				});
			}
		});
	}

	@Scheduled(cron = "${aws.s3.digester.cron:-}")
	public void digest() {
		stop.set(false);
		if (props.getS3().getDigester().getCronProps().isEnabled()) {
			log.info("starting digest with cron props: {}", props.getS3().getDigester().getCronProps().toString());
			LocalDate startDate = LocalDate.now().minusDays(props.getS3().getDigester().getCronProps().getDaysBack());
			digest(startDate, LocalDate.now(), props.getS3().getDigester().getCronProps().getPrefixTemplate(),
					props.getS3().getDigester().getCronProps().getDateFormat(), getPosKeyList());
			log.info("completed digest with cron props");
		} else {
			log.info("starting s3 digest with no parameters");
			digest(null);
		}
		afterDigest();
	}

	private List<String> getPosKeyList() {
		try {
			Resource resource = resourceLoader
					.getResource(props.getS3().getDigester().getCronProps().getPosKeyListSource());
			String content = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
			String[] lines = content.split("\n");
			ArrayList<String> list = new ArrayList<String>(lines.length);
			for (int i = 0; i < lines.length; i++) {
				if (Objects.nonNull(lines[i]) && Objects.nonNull(Strings.trimToNull(lines[i]))) {
					list.add(Strings.trimToNull(lines[i]));
				}
			}
			return list;
		} catch (Exception e) {
			log.error("error loading poskeys file", e);
			return Arrays.asList();
		}
	}

	protected void digest(String prefix) {
		if (lock.tryLock()) {
			log.debug("beginning s3 digest");
			try {

				final AtomicInteger eligibleCount = new AtomicInteger(0);
				final AtomicInteger processedCount = new AtomicInteger(0);
				final AtomicInteger skippedCount = new AtomicInteger(0);
				final AtomicInteger erroredCount = new AtomicInteger(0);

				// get prefix from digester props, but override if a prefix was passed into this
				// method
				String _prefix = props.getS3().getDigester().getPrefix();
				if (Objects.nonNull(prefix)) {
					_prefix = prefix;
				}
				boolean usePrefix = Objects.nonNull(_prefix);

				final Builder s3RequestBuilder = ListObjectsV2Request.builder().bucket(props.getS3().getBucketName())
						.maxKeys(props.getS3().getDigester().getMaxPerPage());
				if (usePrefix) {
					s3RequestBuilder.prefix(_prefix);
				}
				if (Objects.nonNull(props.getS3().getDigester().getDelimiter())) {
					s3RequestBuilder.delimiter(props.getS3().getDigester().getDelimiter());
				}

				ListObjectsV2Iterable objectPages = s3.listObjectsV2Paginator(s3RequestBuilder.build());
				BreakingForEach.forEach(objectPages.stream(), (page, pageBreaker) -> {
					if (stop.get()) {
						pageBreaker.stop();
					} else {
						final ArrayList<CompletableFuture<ImportedFile>> completables = new ArrayList<>();
						BreakingForEach.forEach(page.contents().parallelStream(), (object, breaker) -> {
							if (stop.get()) {
								breaker.stop();
							} else {
								final String key = object.key();
								if (this.s3FileNameUtil.eligibleKey(key)) {
									eligibleCount.getAndIncrement();
									eligibleCountTotal.getAndIncrement();

									final AlohaTable table = s3FileNameUtil.extractTableName(key);
									final Long posKey = s3FileNameUtil.extractPosKey(key);

									final DbfImporter<? extends ImportIdentifiable, ? extends Serializable> dbfImporter = this.importerResolver
											.getImporter(table);
									final Optional<ImportedFile> check = dbfImporter.checkImport(key);

									if (check.isPresent() && !check.get().getStatus().equals(ImportState.REPROCESS)) {
										log.debug("file already imported: " + key);
									} else {
										if (check.isPresent()
												&& check.get().getStatus().equals(ImportState.REPROCESS)) {
											log.info("reprocessing file: " + key);
										}
										processedCount.getAndIncrement();
										processedCountTotal.getAndIncrement();

										final ResponseInputStream<GetObjectResponse> response = s3
												.getObject(GetObjectRequest.builder()
														.bucket(props.getS3().getBucketName()).key(key).build());
										try {
											byte[] bytes = response.readAllBytes();
											final ImportProcessingRequest importRequest = new ImportProcessingRequest(
													new ByteArrayInputStream(bytes), posKey, key, bytes.length);
											final CompletableFuture<ImportedFile> completable = dbfImporter
													.process(importRequest);
											if (Objects.isNull(completable)) {
												throw new RuntimeException(
														"dbfImporter.process return null. key:" + key);
											}
											completables.add(completable);
										} catch (Exception e) {
											erroredCount.getAndIncrement();
											erroredCountTotal.getAndIncrement();
											if (props.getS3().getDigester().isStopOnS3ReadException()) {
												throw new RuntimeException("failed to read s3 object: " + key);
											}
										}

									}
								} else {
									skippedCount.getAndIncrement();
									skippedCountTotal.getAndIncrement();
								}
							}
						});
						// If this page contained files to process
						if (completables.size() > 0) {
							CompletableFuture<Void> completablePage = CompletableFuture
									.allOf(completables.toArray(new CompletableFuture[completables.size()]));
							completablePage.join();
							if (completablePage.isCompletedExceptionally()) {
								throw new RuntimeException("failed. check logs");
							}
						}
					}
				});

				if (stop.get()) {
					log.info("stopped digester");
				}
				log.debug(String.format("completed s3 digest. eligible: %s processed: %s skipped: %s errored: %s",
						eligibleCount.get(), processedCount.get(), skippedCount.get(), erroredCount.get()));
			} catch (Exception ex) {
				log.error("failed during import", ex);
			} finally {
				lock.unlock();
			}
		} else {
			log.info("bucket digester is already running");
		}

	}



	private void afterDigest() {
		log.info("loading afterDigestSqlSource: {}", props.getS3().getDigester().getAfterDigestSqlSource());
		Resource resource = resourceLoader.getResource(props.getS3().getDigester().getAfterDigestSqlSource());
		executeScript(resource);
	}
	
	public void executeScript(Resource script) {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(script);
		try {
			log.info("executing script");
			databasePopulator.execute(datasource);
			log.info("completed script execution");
		} catch (ScriptException e) {
			log.error("Error executing script , script: {}. Error: {}", script.getFilename(), e.getMessage(), e);
		}
	}
}
