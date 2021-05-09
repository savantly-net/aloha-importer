package net.savantly.aloha.importer.domain.tax;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class TaxImporter extends AbstractDbfImporter<Tax, TaxId> {

	public TaxImporter(ImportIdentifiableRepository<Tax, TaxId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Tax.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.TAX;
	}

}
