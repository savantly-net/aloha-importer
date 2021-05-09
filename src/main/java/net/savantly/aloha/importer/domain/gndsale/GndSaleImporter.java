package net.savantly.aloha.importer.domain.gndsale;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GndSaleImporter extends AbstractDbfImporter<GndSale, Long> {

	public GndSaleImporter(ImportIdentifiableRepository<GndSale, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GndSale.class);
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GNDSALE;
	}

}
