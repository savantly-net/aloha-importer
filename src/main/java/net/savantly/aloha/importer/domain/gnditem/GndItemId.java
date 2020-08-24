package net.savantly.aloha.importer.domain.gnditem;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GndItemId implements Serializable {

	private Long posKey;
	private Long entryid;
	
}
