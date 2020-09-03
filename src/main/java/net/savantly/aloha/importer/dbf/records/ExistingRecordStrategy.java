package net.savantly.aloha.importer.dbf.records;

/**
 * Skip inserting a record if it already exists, or throw an exception
 * 
 * @author jeremybranham
 *
 */
public enum ExistingRecordStrategy {
	SKIP_IF_EQUAL,
	SKIP_ALWAYS,
	FAIL
}
