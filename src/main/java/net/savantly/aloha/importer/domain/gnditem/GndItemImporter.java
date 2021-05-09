package net.savantly.aloha.importer.domain.gnditem;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndItemImporter extends AbstractDbfImporter<GndItem, Long> {

	public GndItemImporter(ImportIdentifiableRepository<GndItem, Long> repo, ImportedFileRepository importedFileRepo) {
		super(repo, importedFileRepo, GndItem.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDITEM;
	}

}
