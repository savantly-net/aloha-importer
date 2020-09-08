package net.savantly.aloha.importer.domain.gndtndr;

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
@IdClass(GndTndrId.class)
public class GndTndr implements ImportIdentifiable {
	
	@Id
	private Long id;

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long employee;

	@Column(name = "item_check")
	private Long check;

	private Date date;

	private Date sysdate;

	private Long type;

	private Long typeid;
	@Column(length = 20)
	private String ident;
	@Column(length = 20)
	private String auth;
	@Column(length = 4)
	private String exp;
	@Column(length = 20)
	private String name;
	@Column(length = 6)
	private String unit;

	private BigDecimal amount;

	private BigDecimal tip;

	private BigDecimal nr;
	@Column(length = 1)
	private String track;

	private Long houseid;

	private Long tippable;

	private Long manager;

	private Long hour;

	private Long minute;


	private BigDecimal autograt;

	private Long strunit;

	private Long revenue;

	private Long occasion;

	private Long source;

	private Long pmspostd;

	private Long drawer;

	private BigDecimal wagegrat;

}