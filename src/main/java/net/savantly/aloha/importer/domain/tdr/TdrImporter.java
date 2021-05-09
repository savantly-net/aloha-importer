package net.savantly.aloha.importer.domain.tdr;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class TdrImporter extends AbstractDbfImporter<Tdr, TdrId> {

	public TdrImporter(ImportIdentifiableRepository<Tdr, TdrId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Tdr.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.TDR;
	}

}
