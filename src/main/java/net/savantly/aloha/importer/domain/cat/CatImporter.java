package net.savantly.aloha.importer.domain.cat;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class CatImporter extends AbstractDbfImporter<Cat, CatId> {

	private CrudRepository<Cat, CatId> catRepo;

	public CatImporter(CrudRepository<Cat, CatId> repo, ImportedFileRepository importedFileRepo) {
		super(repo, importedFileRepo, Cat.class);
		this.catRepo = repo;
	}
	
	@Override
	protected boolean isOkToAdd(Cat item) {
		
		return super.isOkToAdd(item);
		
		/*
		// see if there is an item in the db with this id
		Optional optItem = catRepo.findById(new CatId().setId(item.getId()).setPosKey(item.getPosKey()));
		
		//if there is
		if(optItem.isPresent()) {
			// see if the new item equals the old item
			// the subclass needs to implement the equals method, ignoring fields like import date
			if(item.equals(optItem.get())) {
				// if it's the same, dont import
				return false;
			} else {
				return true;
			}
		} else { // the record didnt already exist in the DB
			return true;
		}
		*/
		
		/*
		// see if there is an item in the db with the shared id
		Optional optItem = catRepo.findOneBySharedId(item.getSharedId());
		
		//if there is
		if(optItem.isPresent()) {
			// see if the new item equals the old item
			// the subclass needs to implement the equals method, ignoring the pos/import specific data
			if(item.equals(optItem.get())) {
				return false;
			} else {
				return true;
			}
		} else { // the record didnt already exist in the DB
			return true;
		}
		*/
	}

	@Override
	protected boolean useSingleThreadedExecutor() {
		return true;
	}

}
