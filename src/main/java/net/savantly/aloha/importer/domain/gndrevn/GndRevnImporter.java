package net.savantly.aloha.importer.domain.gndrevn;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndRevnImporter extends AbstractDbfImporter<GndRevn, Long> {

	public GndRevnImporter(ImportIdentifiableRepository<GndRevn, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndRevn.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDREVN;
	}

}
