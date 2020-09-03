package net.savantly.aloha.importer.domain.gndrevn;

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
public class GndRevn implements ImportIdentifiable {
	
	@Id
	@GeneratedValue
	private Long recordId;

	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long employee;

	@Column(name = "item_check")
	private Long check;

	private Long period;

	private Long revid;

	private Long type;

	private Long typeid;

	private BigDecimal amount;

	private Long openhour;

	private Long openmin;

	private Long orderhour;

	private Long ordermin;

	private Long closehour;

	private Long closemin;

	private Long unit;

	private Date dob;

	private Long occasion;

	private Long revid2;

}