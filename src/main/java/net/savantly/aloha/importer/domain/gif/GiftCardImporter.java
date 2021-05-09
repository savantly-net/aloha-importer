package net.savantly.aloha.importer.domain.gif;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GiftCardImporter extends AbstractDbfImporter<GiftCard, Long>{

	public GiftCardImporter(ImportIdentifiableRepository<GiftCard, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GiftCard.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.GIF;
	}

}
