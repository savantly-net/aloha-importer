package net.savantly.aloha.importer.domain.gndlbsum;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndLbSumImporter extends AbstractDbfImporter<GndLbSum, Long> {

	public GndLbSumImporter(ImportIdentifiableRepository<GndLbSum, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndLbSum.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDLBSUM;
	}

}
