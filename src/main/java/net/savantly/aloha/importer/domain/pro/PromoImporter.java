package net.savantly.aloha.importer.domain.pro;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class PromoImporter extends AbstractDbfImporter<Promo, PromoId> {

	public PromoImporter(CrudRepository<Promo, PromoId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Promo.class);
	}

	@Override
	protected boolean useSingleThreadedExecutor() {
		return true;
	}
}
