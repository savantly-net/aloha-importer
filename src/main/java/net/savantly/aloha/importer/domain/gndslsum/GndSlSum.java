package net.savantly.aloha.importer.domain.gndslsum;

import java.math.BigDecimal;
import java.util.Date;

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
public class GndSlSum implements ImportIdentifiable {
	
	@Id
	@GeneratedValue
	private Long recordId;

	private Long posKey;
	private Long importId;
	private Date importDate;

	private Date dob;

	private Long storeid;

	private Long regionid;

	private Long occasionid;

	private Long keyvolume;

	private Long revid;

	private Long catid;

	private Long starthour;

	private Long startmin;

	private Long stophour;

	private Long stopmin;

	private BigDecimal amount;

}