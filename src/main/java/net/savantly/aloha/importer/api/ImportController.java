package net.savantly.aloha.importer.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.DbfImporter;
import net.savantly.aloha.importer.dbf.DbfToJava;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;
import net.savantly.aloha.importer.dbf.ImportProcessingRequest;
import net.savantly.aloha.importer.domain.cat.CatImporter;
import net.savantly.aloha.importer.domain.gndadjck.GndAdjAckImporter;
import net.savantly.aloha.importer.domain.gnddrwr.GndDrwrImporter;
import net.savantly.aloha.importer.domain.gnditem.GndItemImporter;
import net.savantly.aloha.importer.domain.gndline.GndLineImporter;
import net.savantly.aloha.importer.domain.gndrevn.GndRevnImporter;
import net.savantly.aloha.importer.domain.gndsale.GndSaleImporter;
import net.savantly.aloha.importer.domain.gndslsum.GndSlSumImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.domain.mod.ModImporter;
import net.savantly.aloha.importer.domain.modcode.ModCodeImporter;
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
	
	private final ApplicationContext context;
	private final S3Client s3Client;
	private final DbfToJava dfToJava = new DbfToJava();
	
	@PostMapping("/s3")
	public ResponseEntity<ImportedFile> loadFromS3(@RequestBody S3ImportRequest request) throws IOException {

		ResponseInputStream<GetObjectResponse> response = 
				s3Client.getObject(GetObjectRequest.builder().bucket(request.getBucket()).key(request.getFileKey()).build());
		byte[] bytes = response.readAllBytes();
		
		DbfImporter<? extends ImportIdentifiable, ? extends Serializable> importer = getImporter(request.getTable());
		ImportedFile result = importer.process(new ImportProcessingRequest(new ByteArrayInputStream(bytes), request.getPosKey(), request.getFileKey()));
		switch (result.getStatus()) {
		case DONE: 
			return ResponseEntity.ok(result);
		case PROCESSING:
			return ResponseEntity.accepted().body(result);
		default: 
			return ResponseEntity.badRequest().body(result);
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

	private DbfImporter<? extends ImportIdentifiable, ? extends Serializable> getImporter(AlohaTable table) {
		switch (table) {
		case CAT:
			return this.context.getBean(CatImporter.class);
		case GNDADJACK:
			return this.context.getBean(GndAdjAckImporter.class);
		case GNDDRWR:
			return this.context.getBean(GndDrwrImporter.class);
		case GNDITEM:
			return this.context.getBean(GndItemImporter.class);
		case GNDLINE:
			return this.context.getBean(GndLineImporter.class);
		case GNDREVN:
			return this.context.getBean(GndRevnImporter.class);
		case GNDSALE:
			return this.context.getBean(GndSaleImporter.class);
		case GNDSLSUM:
			return this.context.getBean(GndSlSumImporter.class);
		case MOD:
			return this.context.getBean(ModImporter.class);
		case MODCODE:
			return this.context.getBean(ModCodeImporter.class);
		default:
			throw new RuntimeException("couldn't find importer bean for " + table.name());
		}
	}
}
