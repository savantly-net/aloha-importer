package net.savantly.aloha.importer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.DbfImporter;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;
import net.savantly.aloha.importer.dbf.ImportProcessingRequest;
import net.savantly.aloha.importer.dbf.ImporterBeanResolver;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;

@SpringBootTest
class ImporterApplicationTests {
	
	private final Logger log = LoggerFactory.getLogger(ImporterApplicationTests.class);

	@Autowired
	ImporterBeanResolver importerResolver;

	@Value("classpath:/CAT.dbf")
	Resource catFile;

	@Value("classpath:/TDR.dbf")
	Resource tdrFile;

	@Value("classpath:/GNDITEM.dbf")
	Resource gndItemFile;

	long pos = 11111L;

	@Test
	void contextLoads() {
	}

	@Test
	void importCAT() throws IOException {
		String fileName = "CAT.dbf";
		CompletableFuture<ImportedFile> result = importFile(fileName, AlohaTable.CAT, catFile);
		result.join();
		Assertions.assertTrue(result.isDone());
		Assertions.assertFalse(result.isCompletedExceptionally());
	}

	@Test
	void importTDR() throws IOException {
		String fileName = "TDR.dbf";
		CompletableFuture<ImportedFile> result = importFile(fileName, AlohaTable.TDR, tdrFile);
		result.join();
		Assertions.assertTrue(result.isDone());
		Assertions.assertFalse(result.isCompletedExceptionally());
	}

	@Test
	void importGNDITEM() throws IOException {
		String fileName = "GNDITEM.dbf";
		CompletableFuture<ImportedFile> result = importFile(fileName, AlohaTable.GNDITEM, gndItemFile);
		result.join();
		Assertions.assertTrue(result.isDone());
		Assertions.assertFalse(result.isCompletedExceptionally());
	}
	
	@Test
	void multiThreadStress() throws IOException {

		ArrayList<CompletableFuture<ImportedFile>> completables = new ArrayList<>();
		
		for (int i = 0; i < 15; i++) {
			
			final String catFileName = String.format("CAT_%s", i);
			completables.add(importFile(catFileName, AlohaTable.CAT, catFile));

			final String gndItemFileName = String.format("GNDITEM_%s", i);
			completables.add(importFile(gndItemFileName, AlohaTable.GNDITEM, gndItemFile));

			final String tdrFileName = String.format("TDRITEM_%s", i);
			completables.add(importFile(tdrFileName, AlohaTable.TDR, tdrFile));
		}
		

		// these should come back as already processed
		for (int i = 0; i < 10; i++) {
			
			final String catFileName = String.format("CAT_%s", i);
			completables.add(importFile(catFileName, AlohaTable.CAT, catFile));

			final String gndItemFileName = String.format("GNDITEM_%s", i);
			completables.add(importFile(gndItemFileName, AlohaTable.GNDITEM, gndItemFile));

			final String tdrFileName = String.format("TDRITEM_%s", i);
			completables.add(importFile(tdrFileName, AlohaTable.TDR, tdrFile));

		}
		
		// add several of the same to try triggering race condition
		for (int i = 40; i < 60; i++) {
			final String tdrFileName = String.format("TDRITEM_%s", i);
			completables.add(importFile(tdrFileName, AlohaTable.TDR, tdrFile));
		}
		
		CompletableFuture<Void> result = CompletableFuture.allOf(completables.toArray(new CompletableFuture[20]));
		log.info("exception: {}", result.isCompletedExceptionally());
		log.info("done: {}", result.isDone());
		result.join();
		Assertions.assertTrue(result.isDone());
		Assertions.assertFalse(result.isCompletedExceptionally());
	}

	CompletableFuture<ImportedFile> importFile(String fileName, AlohaTable table, Resource resource) throws IOException {
		DbfImporter<? extends ImportIdentifiable, ? extends Serializable> dbfImporter = this.importerResolver
				.getImporter(table);

		byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
		ImportProcessingRequest importRequest = new ImportProcessingRequest(new ByteArrayInputStream(bytes), pos,
				fileName);
		return dbfImporter.process(importRequest);
	}
}
