package net.savantly.aloha.importer.domain.gndadjck;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndAdjAckImporter extends AbstractDbfImporter<GndAdjAck, Long> {

	public GndAdjAckImporter(CrudRepository<GndAdjAck, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndAdjAck.class);
	}

}
