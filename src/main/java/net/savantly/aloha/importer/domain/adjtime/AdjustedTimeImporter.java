package net.savantly.aloha.importer.domain.adjtime;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class AdjustedTimeImporter extends AbstractDbfImporter<AdjustedTime, Long> {

	public AdjustedTimeImporter(ImportIdentifiableRepository<AdjustedTime, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, AdjustedTime.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.ADJTIME;
	}

}
