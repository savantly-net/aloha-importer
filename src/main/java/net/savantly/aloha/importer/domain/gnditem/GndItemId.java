package net.savantly.aloha.importer.domain.gnditem;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
import net.savantly.aloha.importer.domain.cat.CatId;

@Accessors(chain = true)
@Data
public class GndItemId implements Serializable {
	private Long posKey;
	private Long entryid;
	private Date dob;
}