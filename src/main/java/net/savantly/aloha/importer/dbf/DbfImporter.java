package net.savantly.aloha.importer.dbf;

import java.io.Serializable;
import java.util.Optional;

import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;

public interface DbfImporter<T extends ImportIdentifiable, ID extends Serializable> {

	ImportedFile process(ImportProcessingRequest request);
	Optional<ImportedFile> checkImport(String fileName);

}