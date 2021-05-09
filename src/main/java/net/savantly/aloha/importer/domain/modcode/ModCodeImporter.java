package net.savantly.aloha.importer.domain.modcode;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class ModCodeImporter extends AbstractDbfImporter<ModCode, Long> {

	public ModCodeImporter(ImportIdentifiableRepository<ModCode, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, ModCode.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.MODCODE;
	}

}
