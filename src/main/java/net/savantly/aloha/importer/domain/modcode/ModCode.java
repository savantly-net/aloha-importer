package net.savantly.aloha.importer.domain.modcode;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@Table(indexes = { @Index(columnList = "posKey", unique = false) })
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ModCode implements ImportIdentifiable {

	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long usernumber;
	@Id
	private Long number;
	
	@Column(length = 20, name = "item_desc")
	private String desc;
	@Column(length = 20)
	private String mod_name;
	@Column(length = 4)
	private String indicator;
	@Column(length = 1)
	private String active;

	private Long quantity;

	private BigDecimal color;
	@Column(length = 1)
	private String intensity;
	@Column(length = 1)
	private String reverse;
	@Column(length = 1)
	private String blink;

	private Long ownerid;

	@Column(length = 1)
	private String sysrecord;
	@Column(length = 12)
	private String image;
	@Column(length = 1)
	private String pizzamod;
	@Column(length = 1)
	private String btncolor;

	private BigDecimal btncolorr;

	private BigDecimal btncolorg;

	private BigDecimal btncolorb;

	private BigDecimal mltply;

	private BigDecimal mltplyinc;
	@Column(length = 1)
	private String afctprice;


}