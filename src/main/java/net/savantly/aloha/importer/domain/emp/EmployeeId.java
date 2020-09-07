package net.savantly.aloha.importer.domain.emp;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeId implements Serializable {

	private Long posKey;
	private Long id;
}
