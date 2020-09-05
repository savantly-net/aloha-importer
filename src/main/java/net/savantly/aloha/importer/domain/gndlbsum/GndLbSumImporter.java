package net.savantly.aloha.importer.domain.gndlbsum;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndLbSumImporter extends AbstractDbfImporter<GndLbSum, Long> {

	public GndLbSumImporter(CrudRepository<GndLbSum, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndLbSum.class);
	}

}
