package net.savantly.aloha.importer.domain.odr;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class OrderModeId implements Serializable {

	private Long posKey;
	private Long id;
}
