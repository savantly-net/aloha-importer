package net.savantly.aloha.importer.domain.gnditem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.domain.common.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndItemImporter extends AbstractDbfImporter<GndItem, GndItemId> {

	public GndItemImporter(CrudRepository<GndItem, GndItemId> repo, ImportedFileRepository importedFileRepo) {
		super(repo, importedFileRepo, GndItem.class);
	}

}
