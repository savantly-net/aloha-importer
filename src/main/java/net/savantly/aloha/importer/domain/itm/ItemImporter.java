package net.savantly.aloha.importer.domain.itm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class ItemImporter extends AbstractDbfImporter<Item, Long> {

	public ItemImporter(CrudRepository<Item, Long> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Item.class);
	}

}
