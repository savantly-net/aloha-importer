package net.savantly.aloha.importer.domain.cat;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CatId implements Serializable{

	private Long id;
	private Long posKey;
}
