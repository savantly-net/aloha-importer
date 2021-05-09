package net.savantly.aloha.importer.domain.cat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class CatImporter extends AbstractDbfImporter<Cat, CatId> {

	private CrudRepository<Cat, CatId> catRepo;

	public CatImporter(ImportIdentifiableRepository<Cat, CatId> repo, ImportedFileRepository importedFileRepo) {
		super(repo, importedFileRepo, Cat.class);
		this.catRepo = repo;
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.CAT;
	}

}
