package net.savantly.aloha.importer.domain.pro;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class PromoImporter extends AbstractDbfImporter<Promo, PromoId> {

	public PromoImporter(ImportIdentifiableRepository<Promo, PromoId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Promo.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.PRO;
	}

}
