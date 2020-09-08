package net.savantly.aloha.importer.domain.pro;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PromoId implements Serializable {

	private Long posKey;
	private Long id;
}
