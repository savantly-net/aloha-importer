package net.savantly.aloha.importer.domain.common;

import java.util.Date;

public interface ImportIdentifiable {

	Long getPosKey();
	void setPosKey(Long value);
	Long getImportId();
	void setImportId(Long value);
	Date getImportDate();
	void setImportDate(Date value);
}
