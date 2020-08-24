package net.savantly.aloha.importer.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.savantly.aloha.importer.domain.common.ImportProcessingRequest;
import net.savantly.aloha.importer.domain.gnditem.GndItemImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.s3.S3ImportRequest;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImportController {
	
	private final static Logger log = LoggerFactory.getLogger(ImportController.class);
	
	private final GndItemImporter gndItemImporter;
	private final S3Client s3Client;
	
	@PostMapping("/s3")
	public ResponseEntity<ImportedFile> loadFromS3(@RequestBody S3ImportRequest request) throws IOException {

		ResponseInputStream<GetObjectResponse> response = 
				s3Client.getObject(GetObjectRequest.builder().bucket(request.getBucket()).key(request.getFileKey()).build());
		byte[] bytes = response.readAllBytes();
		
		ImportedFile result = this.gndItemImporter.process(new ImportProcessingRequest(new ByteArrayInputStream(bytes), request.getPosKey(), request.getFileKey()));
		switch (result.getStatus()) {
		case DONE: 
			return ResponseEntity.ok(result);
		case PROCESSING:
			return ResponseEntity.accepted().body(result);
		default: 
			return ResponseEntity.badRequest().body(result);
		}
		
	}
}
