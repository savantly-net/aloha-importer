package net.savantly.aloha.importer.domain.gnddrwr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndDrwrImporter extends AbstractDbfImporter<GndDrwr, Long> {

	public GndDrwrImporter(CrudRepository<GndDrwr, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndDrwr.class);
	}

}
