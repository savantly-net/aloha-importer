package net.savantly.aloha.importer.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.savantly.aloha.importer.aws.s3.BucketDigester;
import net.savantly.aloha.importer.aws.s3.S3ImportRequest;
import net.savantly.aloha.importer.dbf.DbfImporter;
import net.savantly.aloha.importer.dbf.DbfToJava;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImportController {
	
	private final static Logger log = LoggerFactory.getLogger(ImportController.class);
	
	private final ApplicationContext context;
	private final ImporterBeanResolver importerResolver;
	private final S3Client s3Client;
	private final ImportedFileRepository importedFileRepository;
	private final DbfToJava dfToJava = new DbfToJava();
	
	@GetMapping({"", "/"})
	public Page<ImportedFile> getImportedFiles(
			@RequestParam(name = "name", required = false) String name, 
			@RequestParam(name = "status", required = false) ImportState state,
			Pageable page) {
		page = Objects.nonNull(page) ? page: PageRequest.of(0, 50);
		
		if (Objects.nonNull(name)) {
			Optional<ImportedFile> opt = importedFileRepository.findByName(name);
			if(opt.isPresent()) {
				return new PageImpl<ImportedFile>(Arrays.asList(opt.get()));
			} else {
				return Page.empty();
			}
		} else if (Objects.nonNull(state)) {
			return importedFileRepository.findByStatus(state, page);
		} else {
			return importedFileRepository.findAll(page);
		}
		
	}
	
	@PostMapping("/state/error/reprocess")
	public ResponseEntity<Iterable<ImportedFile>> setErrorStateToReprocess(){
		Page<ImportedFile> files = this.importedFileRepository.findByStatus(ImportState.ERROR, Pageable.unpaged());
		files.forEach(f -> {
			f.setStatus(ImportState.REPROCESS);
		});
		return ResponseEntity.ok(this.importedFileRepository.saveAll(files));
	}
	
	@PostMapping("/s3")
	public ResponseEntity<ImportedFile> loadFromS3(@RequestBody S3ImportRequest request) throws IOException {

		ResponseInputStream<GetObjectResponse> response = 
				s3Client.getObject(GetObjectRequest.builder().bucket(request.getBucket()).key(request.getFileKey()).build());
		byte[] bytes = response.readAllBytes();
		
		DbfImporter<? extends ImportIdentifiable, ? extends Serializable> importer = importerResolver.getImporter(request.getTable());
		CompletableFuture<ImportedFile> result = importer.process(new ImportProcessingRequest(new ByteArrayInputStream(bytes), request.getPosKey(), request.getFileKey()));
		ImportedFile importedFile = result.join();
		switch (importedFile.getStatus()) {
		case DONE: 
			return ResponseEntity.ok(importedFile);
		case PROCESSING:
			return ResponseEntity.accepted().body(importedFile);
		default: 
			return ResponseEntity.badRequest().body(importedFile);
		}
	}
	
	@PostMapping("/s3/digest")
	public ResponseEntity<String> digestBucket(){
		if(context.containsBean(BucketDigester.BEAN_NAME)) {
			BucketDigester bean = this.context.getBean(BucketDigester.class);
			Runnable r = new Runnable() {
		         public void run() {
		        	 bean.digest();
		         }
		     };
		     new Thread(r).start();
			return ResponseEntity.ok("started");
		} else {
			return ResponseEntity.ok("bucket digest is not enabled");
		}
	}
	
	@PostMapping("/class-dump")
	public ResponseEntity<String> dumpClass(@RequestBody S3ImportRequest request) throws IOException {
		
		String className = getClassName(request.getFileKey());
		
		ResponseInputStream<GetObjectResponse> response = 
				s3Client.getObject(GetObjectRequest.builder().bucket(request.getBucket()).key(request.getFileKey()).build());
		byte[] bytes = response.readAllBytes();
		
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		String result = dfToJava.generate(stream, className);
		return ResponseEntity.ok(result);
	}

	private String getClassName(String fileKey) {
		Matcher matcher = Pattern.compile("/.dbf/i", Pattern.CASE_INSENSITIVE).matcher(fileKey);
		String[] parts = matcher.replaceAll("").split("/");
		return parts[parts.length-1];
	}

	
}
