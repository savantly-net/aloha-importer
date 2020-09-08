package net.savantly.aloha.importer.domain.gndtndr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndTndrImporter extends AbstractDbfImporter<GndTndr, GndTndrId> {

	public GndTndrImporter(CrudRepository<GndTndr, GndTndrId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndTndr.class);
	}

}
