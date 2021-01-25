package net.savantly.aloha.importer.dbf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

import net.savantly.aloha.importer.dbf.records.ChecksForExistingRecord;
import net.savantly.aloha.importer.domain.importedFiles.ImportState;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Transactional
public abstract class AbstractDbfImporter<T extends ImportIdentifiable, ID extends Serializable> implements DbfImporter<T, ID> {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final ObjectMapper mapper = new ObjectMapper();
	private final CrudRepository<T, ID> repo;
	private final ImportedFileRepository importedFiles;
	private final Class<T> clazz;
	
	// Use a single thread for tables don't have a generated ID
	// This prevents a race condition between threads that my be adding a record with the same ID
	private static final ThreadPoolExecutor singleThreadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	
	@PersistenceContext
	private EntityManager em;

	public AbstractDbfImporter(CrudRepository<T, ID> repo, ImportedFileRepository importedFiles, Class<T> clazz) {
		this.repo = repo;
		this.importedFiles = importedFiles;
		this.clazz = clazz;
	}
	
	protected boolean useSingleThreadedExecutor() {
		return false;
	};
	
	@Override
	public Optional<ImportedFile> checkImport(String fileName) {
		return this.importedFiles.findByName(fileName);
	}
	
	@Override
	@Async
    public CompletableFuture<ImportedFile> process(ImportProcessingRequest request) {
		if (this.useSingleThreadedExecutor()) {
			return CompletableFuture.supplyAsync(() -> doWork(request), singleThreadExecutor);
		} else {
			return CompletableFuture.completedFuture(doWork(request));
		}
	}
	
	protected ImportedFile doWork(ImportProcessingRequest request) {
		
		// make sure this file hasn't been processed yet
		Optional<ImportedFile> importCheck = checkImport(request.getImportFileName());
		
		// if the state is not REPROCESS, return the previous result
		if(importCheck.isPresent() && !importCheck.get().getStatus().equals(ImportState.REPROCESS)) {
			log.debug("file has already been processed: {} status: {}", request.getImportFileName(), importCheck.get().getStatus());
			return importCheck.get();
		}
		
		log.debug("beginning import of: {}", request.getImportFileName());
		
		// use the existing REPROCESS record or create a new record
		ImportedFile importedFile = importCheck.orElse(new ImportedFile());
		importedFile.setName(request.getImportFileName());
		importedFile.setStatus(ImportState.PROCESSING);
		importedFile = this.importedFiles.save(importedFile);
		if (em.isJoinedToTransaction()) {
			em.flush();
		}
		
		List<T> items = new ArrayList<T>();
		boolean isError = false;

		long rowCount = 0;
		DBFReader reader = null;
		Date importDate = new Date();
		try {

			reader = new DBFReader(request.getInput());

			int numberOfFields = reader.getFieldCount();
			Map<String, DBFField> tableFields = new HashMap<>();

			for (int i = 0; i < numberOfFields; i++) {
				DBFField tableField = reader.getField(i);
				String tableFieldName = tableField.getName();
				if (log.isTraceEnabled()) {
					log.trace("importing table field: {} {}", tableFieldName, tableField.getType());
				}
				tableFields.put(tableFieldName, tableField);
			}
			
			

			// Now, lets us start reading the rows

			DBFRow row;

			while ((row = reader.nextRow()) != null) {
				rowCount++;
				Map<String,Object> mapRow = new HashMap<String, Object>();
				
				for (String fieldName : tableFields.keySet()) {
					Object value = row.getObject(fieldName);
					mapRow.put(fieldName.toLowerCase(), value);
				}
				T item = this.mapper.convertValue(mapRow, clazz);
				item.setPosKey(request.getPosKey());
				item.setImportDate(importDate);
				item.setImportId(importedFile.getId());
				if(log.isTraceEnabled()) {
					log.trace("importing: {}", item);
				}
				if(isOkToAdd(item)) {
					items.add(item);
				}
			}

			// By now, we have iterated through all of the rows

		} catch (Exception e) {
			log.error("error while importing", e);
			isError = true;
			importedFile.setStatus(ImportState.ERROR);
			importedFile.setErrorMessage(e.toString());
			
		} finally {
			DBFUtils.close(reader);
		}
		
		if(!isError) {
			importedFile.setStatus(ImportState.DONE);
			importedFile.setRows(rowCount);
			importedFile.setImportedRecords(items.size());
			try {
				this.repo.saveAll(items);
			} catch (Exception e) {
				log.error("error while importing", e);
				importedFile.setStatus(ImportState.ERROR);
				importedFile.setErrorMessage(e.toString());
			}
		}
		
		// Save the result status of the import
		this.importedFiles.save(importedFile);
		return importedFile;
	}

	protected boolean isOkToAdd(T item) {
		if(doesCheckForExistingRecord(item)) {
			return doCheckForExistingRecord(item);
		}
		return true;
	}

	private boolean doesCheckForExistingRecord(T item) {
		return ChecksForExistingRecord.class.isAssignableFrom(item.getClass());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean doCheckForExistingRecord(T item) {
		ChecksForExistingRecord checkedItem = (ChecksForExistingRecord)item;
		Optional optItem = repo.findById((ID)checkedItem.getUniqueRecordIdentifier());
		
		//if there is
		if(optItem.isPresent()) {
			// check the strategy
			switch(checkedItem.getExistingRecordStrategy()) {
			case FAIL:
				throw new RuntimeException("A record already exists with this ID, and the strategy is set to FAIL");
			case SKIP_ALWAYS:
				return false;
			case SKIP_IF_EQUAL:
				boolean areEqual = item.equals(optItem.get());
				if(areEqual) {
					return false;
				} else {
					if(log.isTraceEnabled()) {
						try {
							log.trace("merging entity: {}", mapper.writeValueAsString(item));
						} catch (JsonProcessingException e) {
							log.error("failed to serialize entity for trace logger");
						}
					}
					if (em.isJoinedToTransaction()) {
						em.merge(item);
					} else {
						repo.delete((T)optItem.get());
						repo.save(item);
					}
					return false;
				}
			default:
				throw new RuntimeException("how did this happen?");
			}
		} else { // the record didnt already exist in the DB
			return true;
		}
	}

}
