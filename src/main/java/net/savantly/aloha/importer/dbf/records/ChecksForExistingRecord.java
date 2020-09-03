package net.savantly.aloha.importer.dbf.records;

import java.io.Serializable;

import net.savantly.aloha.importer.dbf.ImportIdentifiable;

public interface ChecksForExistingRecord<ID extends Serializable> extends ImportIdentifiable {

	ID getUniqueRecordIdentifier();
	ExistingRecordStrategy getExistingRecordStrategy();
	
}
