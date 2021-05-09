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
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

import net.savantly.aloha.importer.domain.importedFiles.ImportState;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Transactional
public abstract class AbstractDbfImporter<T extends ImportIdentifiable, ID extends Serializable> implements DbfImporter<T, ID> {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final ObjectMapper mapper = new ObjectMapper();
	private final ImportIdentifiableRepository<T, ID> repo;
	private final ImportedFileRepository importedFiles;
	private final Class<T> clazz;
	
	// Use a single thread for tables don't have a generated ID
	// This prevents a race condition between threads that may be adding a record with the same ID
	private static final ThreadPoolExecutor singleThreadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	
	@PersistenceContext
	private EntityManager em;

	public AbstractDbfImporter(ImportIdentifiableRepository<T, ID> repo, ImportedFileRepository importedFiles, Class<T> clazz) {
		this.repo = repo;
		this.importedFiles = importedFiles;
		this.clazz = clazz;
	}
	
	protected boolean hasDeterministicPrimaryKey() {
		return false;
	};
	
	protected abstract AlohaTable getAlohaTable();
	
	@Override
	public Optional<ImportedFile> checkImport(String fileName) {
		return this.importedFiles.findByName(fileName);
	}
	
	@Override
	public void deleteImport(String fileName) {
		Optional<ImportedFile> optImportedFileRecord = importedFiles.findByName(fileName);
		if(optImportedFileRecord.isPresent()) {
			ImportedFile importedFileRecord = optImportedFileRecord.get();
			repo.deleteByImportId(importedFileRecord.getId());
			importedFileRecord.setStatus(ImportState.DELETED);
		}
	}
	
	@Override
	@Async
    public CompletableFuture<ImportedFile> process(ImportProcessingRequest request) {
		if (this.hasDeterministicPrimaryKey()) {
			// use a single thread to prevent race conditions
			return CompletableFuture.supplyAsync(() -> doWork(request), singleThreadExecutor);
		} else {
			// generated primary keys don't cause race condition, so spawn parallel threads
			return CompletableFuture.completedFuture(doWork(request));
		}
	}
	
	protected ImportedFile doWork(ImportProcessingRequest request) {
		return doWork(request, false);
	}
	
	protected ImportedFile doWork(ImportProcessingRequest request, boolean skipIfAlreadyProcessed) {
		
		Optional<ImportedFile> importCheck = checkImport(request.getImportFileName());
		
		if (skipIfAlreadyProcessed) {
			// make sure this file hasn't been processed yet
			// if the state is not REPROCESS, return the previous result
			if(importCheck.isPresent() && !importCheck.get().getStatus().equals(ImportState.REPROCESS)) {
				log.debug("file has already been processed: {} status: {}", request.getImportFileName(), importCheck.get().getStatus());
				return importCheck.get();
			}
		}
		// It's not safe to re-import records with generated keys, so we delete the previously imported records first.
		if (importCheck.isPresent() && !this.hasDeterministicPrimaryKey()) {
			log.info("deleting previously imported records before import: {}", request.getImportFileName());
			this.deleteImport(request.getImportFileName());
		}
		
		log.debug("beginning import of: {}", request.getImportFileName());
		
		// use the existing REPROCESS record or create a new record
		ImportedFile importedFile = importCheck.orElse(new ImportedFile());
		importedFile.setName(request.getImportFileName());
		importedFile.setStatus(ImportState.PROCESSING);
		importedFile.setAlohaTable(this.getAlohaTable());
		importedFile.setSize(request.getSize());
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
				items.add(item);
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
				if (this.hasDeterministicPrimaryKey()) {
					// save each record individually as it will merge if needed
					items.forEach(i -> repo.save(i));
				} else {
					// save a batch since the primary key is generated
					this.repo.saveAll(items);
				}
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

}
