package net.savantly.aloha.importer.domain.tax;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class TaxImporter extends AbstractDbfImporter<Tax, TaxId> {

	public TaxImporter(CrudRepository<Tax, TaxId> repo, ImportedFileRepository importedFiles, Class<Tax> clazz) {
		super(repo, importedFiles, clazz);
	}

}