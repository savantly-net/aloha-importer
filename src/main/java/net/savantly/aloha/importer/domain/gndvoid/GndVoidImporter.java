package net.savantly.aloha.importer.domain.gndvoid;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndVoidImporter extends AbstractDbfImporter<GndVoid, Long> {

	public GndVoidImporter(ImportIdentifiableRepository<GndVoid, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndVoid.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDVOID;
	}

}
