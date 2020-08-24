package net.savantly.aloha.importer.domain.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

import net.savantly.aloha.importer.domain.importedFiles.ImportState;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFile;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

public abstract class AbstractDbfImporter<T extends ImportIdentifiable, ID extends Serializable> implements DbfImporter<T, ID> {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final ObjectMapper mapper = new ObjectMapper();
	private final CrudRepository<T, ID> repo;
	private final ImportedFileRepository importedFiles;
	private final Class<T> clazz;

	public AbstractDbfImporter(CrudRepository<T, ID> repo, ImportedFileRepository importedFiles, Class<T> clazz) {
		this.repo = repo;
		this.importedFiles = importedFiles;
		this.clazz = clazz;
	}
	
	@Override
	public ImportedFile process(ImportProcessingRequest request) {
		
		// make sure this file hasn't been processed yet
		Optional<ImportedFile> importCheck = this.importedFiles.findByName(request.getImportFileName());
		if(importCheck.isPresent()) {
			log.warn("file has already been processed: {} status: {}", request.getImportFileName(), importCheck.get().getStatus());
			return importCheck.get();
		}
		
		log.info("beginning import of: {}", request.getImportFileName());
		
		ImportedFile importedFile = new ImportedFile();
		importedFile.setName(request.getImportFileName());
		importedFile.setStatus(ImportState.PROCESSING);
		importedFile = this.importedFiles.save(importedFile);
		
		List<T> items = new ArrayList<T>();
		boolean isError = false;

		long rowCount = 0;
		DBFReader reader = null;
		try {

			reader = new DBFReader(request.getInput());

			int numberOfFields = reader.getFieldCount();
			Map<String, DBFField> tableFields = new HashMap<>();

			for (int i = 0; i < numberOfFields; i++) {
				DBFField tableField = reader.getField(i);
				String tableFieldName = tableField.getName();
				log.debug("importing table field: {} {}", tableFieldName, tableField.getType());
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
				item.setImportDate(new Date());
				item.setImportId(importedFile.getId());
				if(log.isTraceEnabled()) {
					log.trace("importing: {}", item);
				}
				items.add(item);
			}

			// By now, we have iterated through all of the rows

		} catch (DBFException | IllegalArgumentException | SecurityException e) {
			log.error("{}", e);
			isError = true;
			importedFile.setStatus(ImportState.ERROR);
			
		} finally {
			DBFUtils.close(reader);
		}
		
		if(!isError) {
			importedFile.setStatus(ImportState.DONE);
			importedFile.setRows(rowCount);
			this.repo.saveAll(items);
		}
		
		// Save the result status of the import
		this.importedFiles.save(importedFile);
		
		return importedFile;
	}
}
