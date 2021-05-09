package net.savantly.aloha.importer.domain.cmp;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class CompImporter extends AbstractDbfImporter<Comp, CompId> {

	public CompImporter(ImportIdentifiableRepository<Comp, CompId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Comp.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.CMP;
	}

}
