package net.savantly.aloha.importer.domain.importedFiles;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ImportedFileRepository extends CrudRepository<ImportedFile, Long> {

	Optional<ImportedFile> findByName(String name);
}
