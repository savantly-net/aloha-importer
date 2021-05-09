package net.savantly.aloha.importer.domain.gndadjck;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndAdjAckImporter extends AbstractDbfImporter<GndAdjAck, Long> {

	public GndAdjAckImporter(ImportIdentifiableRepository<GndAdjAck, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndAdjAck.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDADJACK;
	}

}
