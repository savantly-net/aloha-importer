package net.savantly.aloha.importer.domain.odr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class OrderModeImporter extends AbstractDbfImporter<OrderMode, OrderModeId> {

	public OrderModeImporter(CrudRepository<OrderMode, OrderModeId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, OrderMode.class);
	}

}
