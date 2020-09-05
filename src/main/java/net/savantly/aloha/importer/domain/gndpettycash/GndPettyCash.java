package net.savantly.aloha.importer.domain.gndpettycash;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@Table(indexes = { @Index(columnList = "posKey", unique = false) })
public class GndPettyCash implements ImportIdentifiable {

	@Id
	@GeneratedValue
	private Long recordId;
	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long occasion;

	private Long employee;

	private Long drawer;

	private Long hour;

	private Long minute;

	private Long second;

	private Long type;

	private Long petycashid;

	private BigDecimal amount;
	@Column(length = 25)
	private String descrption;

}