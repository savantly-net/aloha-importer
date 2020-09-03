package net.savantly.aloha.importer.domain.gnditem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndItemImporter extends AbstractDbfImporter<GndItem, Long> {

	public GndItemImporter(CrudRepository<GndItem, Long> repo, ImportedFileRepository importedFileRepo) {
		super(repo, importedFileRepo, GndItem.class);
	}

}
