package net.savantly.aloha.importer.domain.gnditem;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
public class GndItem implements ImportIdentifiable {

	@Id
	@GeneratedValue
	private Long recordId;
	
	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long entryid;
	private Long type;
	private Long employee;

	@Column(name = "item_check")
	private Long check;
	private Long item;
	private Long parent;
	private Long category;
	private Long mode;
	private Long period;
	private Long hour;
	private Long minute;
	private Long taxid;
	private Long revid;
	private Long termid;
	private Long menu;
	private Long origin;
	private BigDecimal price;
	private Long modcode;
	private Long unit;
	private Date dob;
	private Date sysdate;
	private Long seat;
	private Long occasion;
	private Long quantity;
	private Long taxid2;
	private BigDecimal discpric;
	private Long revid2;
	private Long concept;
	private BigDecimal incltax;
	private Long course;
	private String bohname;
	private String bohnameprn;
	private int modfunc;
	private Long qcid;
	private BigDecimal excltax;
	private Long parentmod;
	@JsonProperty(value = "etime_hour")
	private int etimeHour;
	@JsonProperty(value = "etime_min")
	private int etimeMin;
	@JsonProperty(value = "etime_sec")
	private int etimeSec;
	private Long tableid;
	private Long qtyunit;
	private Long substitutd;
	private Long portion;
	private Long gcpid;
	private BigDecimal orgprice;
	private Long occtaxid;
	private Long parentenid;

}
