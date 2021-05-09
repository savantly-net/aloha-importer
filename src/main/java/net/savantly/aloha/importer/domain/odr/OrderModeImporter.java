package net.savantly.aloha.importer.domain.odr;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class OrderModeImporter extends AbstractDbfImporter<OrderMode, OrderModeId> {

	public OrderModeImporter(ImportIdentifiableRepository<OrderMode, OrderModeId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, OrderMode.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.ODR;
	}

}
