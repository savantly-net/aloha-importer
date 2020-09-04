package net.savantly.aloha.importer.domain.tax;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TaxId implements Serializable {

	private Long posKey;
	private Long id;
}
