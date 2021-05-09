package net.savantly.aloha.importer.dbf;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImportIdentifiableRepository<T extends ImportIdentifiable, ID extends Serializable> extends CrudRepository<T, ID> {

	void deleteByImportId(Long importId);
}
