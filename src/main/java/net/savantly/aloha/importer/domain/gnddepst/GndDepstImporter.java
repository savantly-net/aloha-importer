package net.savantly.aloha.importer.domain.gnddepst;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndDepstImporter extends AbstractDbfImporter<GndDepst, GndDepstId>{

	public GndDepstImporter(CrudRepository<GndDepst, GndDepstId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndDepst.class);
	}

}
