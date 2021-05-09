package net.savantly.aloha.importer.domain.itm;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class ItemImporter extends AbstractDbfImporter<Item, Long> {

	public ItemImporter(ImportIdentifiableRepository<Item, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Item.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.ITM;
	}

}
