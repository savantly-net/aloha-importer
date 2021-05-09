package net.savantly.aloha.importer.domain.importedFiles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import net.savantly.aloha.importer.dbf.AlohaTable;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class ImportedFile {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	private long rows;
	private long importedRecords;
	
	@Enumerated(EnumType.STRING)
	private ImportState status;
	
	@Column(length = 1000)
	private String errorMessage;

	@Enumerated(EnumType.STRING)
	private AlohaTable alohaTable;
	
	private Long size;
}
