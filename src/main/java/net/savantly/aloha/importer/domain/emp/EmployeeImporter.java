package net.savantly.aloha.importer.domain.emp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class EmployeeImporter extends AbstractDbfImporter<Employee, EmployeeId> {

	public EmployeeImporter(CrudRepository<Employee, EmployeeId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Employee.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}
}
