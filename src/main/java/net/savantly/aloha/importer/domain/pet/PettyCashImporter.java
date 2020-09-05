package net.savantly.aloha.importer.domain.pet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class PettyCashImporter extends AbstractDbfImporter<PettyCash, PettyCashId>{

	public PettyCashImporter(CrudRepository<PettyCash, PettyCashId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, PettyCash.class);
	}

}
