package net.savantly.aloha.importer.aws.s3;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Event;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class InboundS3EventHandler {

	private final static Logger log = LoggerFactory.getLogger(InboundS3EventHandler.class);
	private final String s3BucketName;
	private final S3FilenameUtil s3FileNameUtil;
	private final S3Client s3;
	private final ImporterBeanResolver importerResolver;
	private final QueueMessagingTemplate queueMessagingTemplate;
	private final String outboundQueue;

    @SqsListener("${aws.sqs.inboundQueue}")
    public void receive(S3EventNotification s3EventNotificationRecord) {
    	S3EventNotificationRecord record = s3EventNotificationRecord.getRecords().get(0);
    	if(S3Event.ObjectCreated.equals(record.getEventNameAsEnum())) {
    		log.info("handling s3 object created");
    		S3EventNotification.S3Entity s3Entity = record.getS3();
        	
        	final String key = s3Entity.getObject().getKey();
        	if (this.s3FileNameUtil.eligibleKey(key)) {

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

    				final ResponseInputStream<GetObjectResponse> response = s3
    						.getObject(GetObjectRequest.builder()
    								.bucket(s3BucketName).key(key).build());
    				try {
    					byte[] bytes = response.readAllBytes();
    					final ImportProcessingRequest importRequest = new ImportProcessingRequest(
    							new ByteArrayInputStream(bytes), posKey, key);
    					final CompletableFuture<ImportedFile> completable = dbfImporter
    							.process(importRequest);
    					if (Objects.isNull(completable)) {
    						throw new RuntimeException(
    								"dbfImporter.process return null. key:" + key);
    					}
    					completable.thenAccept((importedFile) -> {
    						queueMessagingTemplate.convertAndSend(outboundQueue, key);
    					});
    				} catch (Exception e) {
    					log.error(String.format("failed to import key: %s",key), e);
    				}

    			}
    		} else {
    			log.debug("ineligible s3 key for import: {}", key);
    		}
    	} else {
    		log.debug("discarding s3 event: {}, {}", record.getEventNameAsEnum(), record.getS3().getObject().getKey());
    	}
    	
    }
}
