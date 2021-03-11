package net.savantly.aloha.importer.domain.mod;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class ModImporter extends AbstractDbfImporter<Mod, ModId> {

	public ModImporter(CrudRepository<Mod, ModId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Mod.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}
}
