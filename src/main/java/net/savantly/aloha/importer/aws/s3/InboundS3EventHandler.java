package net.savantly.aloha.importer.aws.s3;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.event.S3EventNotification.S3ObjectEntity;
import com.amazonaws.services.s3.model.S3Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.DbfImporter;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;
import net.savantly.aloha.importer.dbf.ImportProcessingRequest;
import net.savantly.aloha.importer.dbf.ImporterBeanResolver;
import net.savantly.aloha.importer.domain.importedFiles.ImportState;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import static java.util.Map.entry;  

@RequiredArgsConstructor
public class InboundS3EventHandler {

	private final static Logger log = LoggerFactory.getLogger(InboundS3EventHandler.class);
	private final String s3BucketName;
	private final S3FilenameUtil s3FileNameUtil;
	private final S3Client s3;
	private final ImporterBeanResolver importerResolver;
	private final QueueMessagingTemplate queueMessagingTemplate;
	private final String outboundQueue;
	private final ImportedFileRepository importedFiles;
	ObjectMapper testMapper = new ObjectMapper();

    @SqsListener("${aws.sqs.inboundQueue}")
    public void receive(S3EventNotification s3EventNotificationRecord) {
    	try {
			log.debug("processing message: {}", testMapper.writeValueAsString(s3EventNotificationRecord));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	s3EventNotificationRecord.getRecords().forEach(eventRecord -> {
    		processRecord(eventRecord);
    	});
    }

	private void processRecord(S3EventNotificationRecord eventRecord) {
    	if(isCreatedEvent(eventRecord)) {
    		log.info("handling s3 object created: {}", eventRecord.getS3().getObject().getKey());
    		S3EventNotification.S3Entity s3Entity = eventRecord.getS3();
        	
        	final String key = s3Entity.getObject().getKey();
        	final String bucket = s3Entity.getBucket().getName();
        	
        	if (this.s3FileNameUtil.eligibleKey(key)) {
        		
        		queueMessagingTemplate.convertAndSend(outboundQueue, Map.of("event", "started", "key", key, "bucket", bucket ));

    			final Optional<ImportedFile> check = importedFiles.findByName(key);
    			if (shouldImport(s3Entity.getObject(), check)) {
    				if (check.isPresent() && check.get().getStatus().equals(ImportState.REPROCESS)) {
    					log.info("reprocessing file: " + key);
    				}
    				processImport(key, bucket, s3Entity.getObject().getSizeAsLong());
    			}
    		} else {
    			log.debug("ineligible s3 key for import: {}", key);
    		}
    	} else {
    		log.debug("discarding s3 event: {}, {}", eventRecord.getEventNameAsEnum(), eventRecord.getS3().getObject().getKey());
    	}
	}

	private boolean shouldImport(S3ObjectEntity s3ObjectEntity, Optional<ImportedFile> check) {
		if (check.isEmpty()) {
			return true;
		} else if (sizeChanged(s3ObjectEntity.getSizeAsLong(), check.get())) {
			log.debug("reprocessing because of file size change: {}", s3ObjectEntity.getKey());
			return true;
		} else if (check.get().getStatus().equals(ImportState.REPROCESS)) {
			log.debug("file flagged for reprocessing: {}", s3ObjectEntity.getKey());
			return true;
		} else {
			log.debug("not importing: {}", s3ObjectEntity.getKey());
			return false;
		}
	}

	private boolean sizeChanged(Long sizeAsLong, ImportedFile importedFile) {
		return Objects.isNull(sizeAsLong) || Objects.isNull(importedFile.getSize()) ||
			!sizeAsLong.equals(importedFile.getSize());
	}

	private void processImport(String key, String bucket, Long size) {

		final Long posKey = s3FileNameUtil.extractPosKey(key);
		final AlohaTable table = s3FileNameUtil.extractTableName(key);
		final DbfImporter<? extends ImportIdentifiable, ? extends Serializable> dbfImporter = this.importerResolver
				.getImporter(table);
		
		final ResponseInputStream<GetObjectResponse> response = s3
				.getObject(GetObjectRequest.builder()
						.bucket(s3BucketName).key(key).build());
		try {
			byte[] bytes = response.readAllBytes();
			final ImportProcessingRequest importRequest = new ImportProcessingRequest(
					new ByteArrayInputStream(bytes), posKey, key, size);
			final CompletableFuture<ImportedFile> completable = dbfImporter
					.process(importRequest);
			if (Objects.isNull(completable)) {
				throw new RuntimeException(
						"dbfImporter.process return null. key:" + key);
			}
			completable.thenAccept((importedFile) -> {
        		queueMessagingTemplate.convertAndSend(outboundQueue, Map.of("event", "imported", "key", key, "bucket", bucket ));
			});
		} catch (Exception e) {
			log.error(String.format("failed to import key: %s",key), e);
    		queueMessagingTemplate.convertAndSend(outboundQueue, Map.of("event", "failed", "key", key, "bucket", bucket ));
		}
	}

	private boolean isCreatedEvent(S3EventNotificationRecord eventRecord) {
		return S3Event.ObjectCreated.equals(eventRecord.getEventNameAsEnum())
			|| S3Event.ObjectCreatedByCompleteMultipartUpload.equals(eventRecord.getEventNameAsEnum())
			|| S3Event.ObjectCreatedByCopy.equals(eventRecord.getEventNameAsEnum())
			|| S3Event.ObjectCreatedByPost.equals(eventRecord.getEventNameAsEnum())
			|| S3Event.ObjectCreatedByPut.equals(eventRecord.getEventNameAsEnum());
	}
}
