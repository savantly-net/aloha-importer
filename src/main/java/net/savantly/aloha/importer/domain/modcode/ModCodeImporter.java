package net.savantly.aloha.importer.domain.modcode;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class ModCodeImporter extends AbstractDbfImporter<ModCode, Long> {

	public ModCodeImporter(CrudRepository<ModCode, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, ModCode.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}
}
