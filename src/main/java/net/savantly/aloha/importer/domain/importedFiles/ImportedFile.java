package net.savantly.aloha.importer.domain.importedFiles;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ImportedFile {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private long rows;
	
	@Enumerated(EnumType.STRING)
	private ImportState status;
}
