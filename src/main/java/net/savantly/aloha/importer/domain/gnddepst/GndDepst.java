package net.savantly.aloha.importer.domain.gnddepst;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@IdClass(GndDepstId.class)
public class GndDepst implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	private Date date;

	private BigDecimal amount;
	@Column(length = 31)
	private String descriptn;

	@Id
	private Long id;

	private Long empid;
	@Column(length = 8)
	private String time;

	private Long storeid;

	private Long regionid;

	private Long occaisnid;
	@Column(length = 31)
	private String descript2;

	private Long termid;

	private Long reasonid1;

	private Long reasonid2;

}