package net.savantly.aloha.importer.domain.mod;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ModId implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long posKey;
	private Long id;

}
