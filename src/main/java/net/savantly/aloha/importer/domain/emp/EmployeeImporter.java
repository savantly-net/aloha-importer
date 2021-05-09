package net.savantly.aloha.importer.domain.emp;

import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.dbf.AbstractDbfImporter;
import net.savantly.aloha.importer.dbf.AlohaTable;
import net.savantly.aloha.importer.dbf.ImportIdentifiableRepository;
import net.savantly.aloha.importer.domain.importedFiles.ImportedFileRepository;

@Service
public class EmployeeImporter extends AbstractDbfImporter<Employee, EmployeeId> {

	public EmployeeImporter(ImportIdentifiableRepository<Employee, EmployeeId> repo, ImportedFileRepository importedFiles) {
		super(repo, importedFiles, Employee.class);
	}

	@Override
	protected boolean hasDeterministicPrimaryKey() {
		return true;
	}

	@Override
	protected AlohaTable getAlohaTable() {
		return AlohaTable.EMP;
	}

}
