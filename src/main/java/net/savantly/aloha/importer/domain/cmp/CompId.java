package net.savantly.aloha.importer.domain.cmp;

import java.io.Serializable;

import lombok.Data;

@Data
public class CompId implements Serializable {
	private Long posKey;
	private Long id;
}
