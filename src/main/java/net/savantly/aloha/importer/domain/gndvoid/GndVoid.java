package net.savantly.aloha.importer.domain.gndvoid;

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
@Table(indexes = { @Index(columnList = "posKey", unique = false), @Index(columnList = "date", unique = false) })
public class GndVoid implements ImportIdentifiable {

	@Id
	@GeneratedValue
	private Long recordId;
	
	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long employee;

	private Long manager;

	@Column(name = "item_check")
	private Long check;
	@Column(length = 15)
	private String tablename;

	private Long item;

	private BigDecimal price;

	private Date date;

	private Date sysdate;

	private Long hour;

	private Long minute;

	private Long reason;
	@Column(length = 1)
	private String inventory;

	private Long unit;

	private Long entryid;

	private Long occasion;

	private Long revid;

}