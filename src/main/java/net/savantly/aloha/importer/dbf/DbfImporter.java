package net.savantly.aloha.importer.dbf;

import java.io.Serializable;

import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;

public interface DbfImporter<T extends ImportIdentifiable, ID extends Serializable> {

	ImportedFile process(ImportProcessingRequest request);

}