package net.savantly.aloha.importer.domain.gndtndr;

import java.io.Serializable;

import lombok.Data;

@Data
public class GndTndrId implements Serializable {

	private Long posKey;
	private Long id;
}
