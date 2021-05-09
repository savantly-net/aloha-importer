package net.savantly.aloha.importer.domain.gnddepst;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndDepstImporter extends AbstractDbfImporter<GndDepst, GndDepstId>{

	public GndDepstImporter(ImportIdentifiableRepository<GndDepst, GndDepstId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndDepst.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDDEPST;
	}

}
