package net.savantly.aloha.importer.domain.tdr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class TdrImporter extends AbstractDbfImporter<Tdr, TdrId> {

	public TdrImporter(CrudRepository<Tdr, TdrId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Tdr.class);
	}

}
