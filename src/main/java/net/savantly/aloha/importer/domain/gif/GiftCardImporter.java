package net.savantly.aloha.importer.domain.gif;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class GiftCardImporter extends AbstractDbfImporter<GiftCard, Long>{

	public GiftCardImporter(CrudRepository<GiftCard, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, GiftCard.class);
	}

	@Override
	protected boolean useSingleThreadedExecutor() {
		return true;
	}
}
