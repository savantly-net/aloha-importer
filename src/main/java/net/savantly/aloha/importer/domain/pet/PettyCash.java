package net.savantly.aloha.importer.domain.pet;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;


@Data
@Entity
@IdClass(PettyCashId.class)
public class PettyCash implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 15)
	private String name;

	private Long type;
	@Column(length = 1)
	private String deposit;

	private Long tender;
	@Column(length = 1)
	private String startbank;
	@Column(length = 1)
	private String descripton;

}