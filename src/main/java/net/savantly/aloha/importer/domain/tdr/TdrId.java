package net.savantly.aloha.importer.domain.tdr;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TdrId implements Serializable {

	private Long posKey;
	private Long id;
}
