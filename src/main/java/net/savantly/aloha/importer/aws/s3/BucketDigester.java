package net.savantly.aloha.importer.aws.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import net.savantly.aloha.importer.aws.AwsConfigProperties;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.DbfImporter;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;
import net.savantly.aloha.importer.dbf.ImportProcessingRequest;
import net.savantly.aloha.importer.dbf.ImporterBeanResolver;
import net.savantly.aloha.importer.domain.importedFiles.ImportState;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

public class BucketDigester {
	
	public final static String BEAN_NAME = "bucketDigester";
	
	private final static Logger log = LoggerFactory.getLogger(BucketDigester.class);

	private final ImporterBeanResolver importerResolver;
	private final AwsConfigProperties props;
	private final S3Client s3;
	private final List<String> keyPatterns;
	private final Pattern tableNamePattern;
	private final Pattern posKeyPattern;
	
	private final Lock lock = new ReentrantLock();
	
	public BucketDigester(AwsConfigProperties props, S3Client s3, ImporterBeanResolver importerResolver) {
		this.props = props;
		this.s3 = s3;
		this.importerResolver = importerResolver;
		
		this.keyPatterns = props.getS3().getDigester().getKeyPatterns();
		
		this.posKeyPattern = Pattern.compile(props.getS3().getDigester().getPosKeyCapturePattern());
		this.tableNamePattern = Pattern.compile(props.getS3().getDigester().getTableNameCapturePattern());
	}
	
	@Scheduled(cron = "${aws.s3.digester.cron:--}")
	public void digest() {
		if (lock.tryLock()) {
			log.info("beginning s3 digest");
			try {
				AtomicInteger processedCount = new AtomicInteger(0);
				AtomicInteger skippedCount = new AtomicInteger(0);
				
				ListObjectsV2Iterable objectPages = s3.listObjectsV2Paginator(ListObjectsV2Request.builder().bucket(props.getS3().getBucketName()).build());
				objectPages.stream().forEach(page -> {
					page.contents().parallelStream().forEach(object -> {
						final String key = object.key();
						if(eligibleKey(key)) {
							processedCount.getAndIncrement();
							final AlohaTable table = extractTableName(key);
							final Long posKey = extractPosKey(key);

							DbfImporter<? extends ImportIdentifiable, ? extends Serializable> dbfImporter = this.importerResolver.getImporter(table);
							Optional<ImportedFile> check = dbfImporter.checkImport(key);
							
							if(check.isPresent() && !check.get().getStatus().equals(ImportState.REPROCESS)) {
								log.debug("file already imported: " + key);
							} else {
								if (check.isPresent() && check.get().getStatus().equals(ImportState.REPROCESS)) {
									log.info("reprocessing file: " + key);
								}
								
								ResponseInputStream<GetObjectResponse> response = 
										s3.getObject(GetObjectRequest.builder().bucket(props.getS3().getBucketName()).key(key).build());
								try {
									byte[] bytes = response.readAllBytes();
									ImportProcessingRequest importRequest = new ImportProcessingRequest(
											new ByteArrayInputStream(bytes), posKey, key);
									dbfImporter.process(importRequest );
								} catch (IOException e) {
									if (props.getS3().getDigester().isStopOnS3ReadException()) {
										throw new RuntimeException("failed to read s3 object: " + key);
									}
								}
								
							}
						} else {
							skippedCount.getAndIncrement();
						}
					});
				});

				log.info(String.format("completed s3 digest. processed: %s skipped: %s", processedCount.get(), skippedCount.get()));
			} catch (Exception ex) {
				log.error("failed during import", ex);
			} finally {
				lock.unlock();
			}
		} else {
			log.info("bucket digester is already running");
		}
		
	}

	private Long extractPosKey(String key) {
		final Matcher matcher = posKeyPattern.matcher(key);
		if (!matcher.matches()) {
			log.info("posKeyPattern failed to match: {}", key);
		}
		final int captureGroup = props.getS3().getDigester().getPosKeyCaptureGroup();
		final String extracted = matcher.group(captureGroup);
		if(Objects.isNull(extracted)) {
			String msg = String.format("Failed to extract pos key from: %s using pattern: %s with capture group: %s", key, posKeyPattern, captureGroup);
			log.error(msg);
			throw new RuntimeException(String.format("failed to match for pos key: {}", matcher.toString()));
		} else {
			try {
				return Long.parseLong(extracted);
			} catch (NumberFormatException e) {
				throw new RuntimeException("the extracted posKey doesn't appear to be in the correct format: " + extracted, e);
			}
		}
	}

	private AlohaTable extractTableName(String key) {
		final Matcher matcher = tableNamePattern.matcher(key);
		if (!matcher.matches()) {
			log.info("tableNamePattern failed to match: {}", key);
		}
		final int captureGroup = props.getS3().getDigester().getTableNameCaptureGroup();
		final String extracted = matcher.group(captureGroup);
		if(Objects.isNull(extracted)) {
			String msg = String.format("Failed to extract name from: %s using pattern: %s with capture group: %s", key, tableNamePattern, captureGroup);
			log.error(msg);
			throw new RuntimeException(String.format("failed to match for table name: {}", matcher.toString()));
		} else {
			try {
				AlohaTable table = AlohaTable.valueOf(extracted.toUpperCase());
				return table;
			} catch (Exception e) {
				throw new RuntimeException("extracted table name doesn't match an AlohaTable value. extracted: " + extracted, e);
			}
		}
	}

	private boolean eligibleKey(String key) {
		for (String pattern : keyPatterns) {
			if (key.matches(pattern)) {
				return true;
			}
		}
		return false;
	}
}
