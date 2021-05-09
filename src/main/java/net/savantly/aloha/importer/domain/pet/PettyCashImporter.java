package net.savantly.aloha.importer.domain.pet;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class PettyCashImporter extends AbstractDbfImporter<PettyCash, PettyCashId>{

	public PettyCashImporter(ImportIdentifiableRepository<PettyCash, PettyCashId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, PettyCash.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.PET;
	}

}
