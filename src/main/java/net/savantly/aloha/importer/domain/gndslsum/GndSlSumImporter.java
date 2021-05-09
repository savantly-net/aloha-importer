package net.savantly.aloha.importer.domain.gndslsum;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndSlSumImporter extends AbstractDbfImporter<GndSlSum, Long> {

	public GndSlSumImporter(ImportIdentifiableRepository<GndSlSum, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndSlSum.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDSLSUM;
	}

}
