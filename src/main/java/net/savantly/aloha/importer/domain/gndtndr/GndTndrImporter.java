package net.savantly.aloha.importer.domain.gndtndr;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndTndrImporter extends AbstractDbfImporter<GndTndr, GndTndrId> {

	public GndTndrImporter(ImportIdentifiableRepository<GndTndr, GndTndrId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndTndr.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDTNDR;
	}

}
