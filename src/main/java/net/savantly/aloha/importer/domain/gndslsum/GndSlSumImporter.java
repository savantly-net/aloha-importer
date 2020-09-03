package net.savantly.aloha.importer.domain.gndslsum;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndSlSumImporter extends AbstractDbfImporter<GndSlSum, Long> {

	public GndSlSumImporter(CrudRepository<GndSlSum, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndSlSum.class);
	}

}
