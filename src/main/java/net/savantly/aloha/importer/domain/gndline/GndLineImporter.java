package net.savantly.aloha.importer.domain.gndline;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndLineImporter extends AbstractDbfImporter<GndLine, GndLineId> {

	public GndLineImporter(ImportIdentifiableRepository<GndLine, GndLineId> repo, ImportedFileRepository importedFileRepo) {
		super(repo, importedFileRepo, GndLine.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDLINE;
	}

}
