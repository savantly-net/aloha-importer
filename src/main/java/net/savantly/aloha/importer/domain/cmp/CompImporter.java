package net.savantly.aloha.importer.domain.cmp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class CompImporter extends AbstractDbfImporter<Comp, CompId> {

	public CompImporter(CrudRepository<Comp, CompId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Comp.class);
	}

	@Override
	protected boolean useSingleThreadedExecutor() {
		return true;
	}
}
