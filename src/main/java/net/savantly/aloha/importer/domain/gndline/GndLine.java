package net.savantly.aloha.importer.domain.gndline;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@IdClass(GndLineId.class)
public class GndLine implements ImportIdentifiable {

	@Id
	private Long posKey;
	@Id
	private Date dob;
	@Id
	private Long entryid;
	@Id
	private Long id;
	
	private Long importId;
	private Date importDate;

	private Long itemid;

	private BigDecimal price;

	private BigDecimal oprice;

	private BigDecimal amt;

	private Long type;

	private Long catid;

	private Long typeid;

	private Long checkid;

	private Long unit;

	private Date sysdate;

	private Long occasion;

	private Long conceptid;

	private Long revenue;

	private Long entryiditm;
}