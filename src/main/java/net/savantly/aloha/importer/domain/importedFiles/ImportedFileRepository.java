package net.savantly.aloha.importer.domain.importedFiles;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImportedFileRepository extends PagingAndSortingRepository<ImportedFile, Long> {

	Optional<ImportedFile> findByName(String name);
	Page<ImportedFile> findByStatus(ImportState status, Pageable page);
}
