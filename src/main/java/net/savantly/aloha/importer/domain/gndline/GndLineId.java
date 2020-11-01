package net.savantly.aloha.importer.domain.gndline;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GndLineId implements Serializable {
	private Long posKey;
	private Long id;
	private Long entryid;
	private Date dob;
}
