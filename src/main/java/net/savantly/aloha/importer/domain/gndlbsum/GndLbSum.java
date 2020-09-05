package net.savantly.aloha.importer.domain.gndlbsum;

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
@Table(indexes = { @Index(columnList = "dob", unique = false), @Index(columnList = "storeid", unique = false) })
public class GndLbSum implements ImportIdentifiable {

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

	private Long jobid;

	private int starthour;

	private int startmin;

	private int stophour;

	private int stopmin;

	private int minutes;

	private BigDecimal cost;

}