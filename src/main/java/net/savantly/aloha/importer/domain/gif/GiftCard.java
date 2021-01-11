package net.savantly.aloha.importer.domain.gif;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;


/**
 * Not sure if everyone does this, but the gift card ID is shared between all the stores, so posKey is not part of PK
 * @author jeremy branham
 *
 */
@Data
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GiftCard implements ImportIdentifiable {

	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 15)
	private String name;
	@Column(length = 1)
	private String active;
	@Column(length = 1)
	private String tracking;
	@Column(length = 1)
	private String assignamt;

	private BigDecimal amount;
	@Column(length = 10)
	private String prefix;

	private Long digits;

	private Long min1;

	private Long min2;

	private Long min3;

	private Long min4;

	private Long min5;

	private Long max1;

	private Long max2;

	private Long max3;

	private Long max4;

	private Long max5;

	private Long taxid;

	private Long vtaxid;

	private Long taxid2;
	@Column(length = 20)
	private String from1;
	@Column(length = 20)
	private String from2;
	@Column(length = 20)
	private String from3;
	@Column(length = 20)
	private String from4;
	@Column(length = 20)
	private String from5;
	@Column(length = 20)
	private String to1;
	@Column(length = 20)
	private String to2;
	@Column(length = 20)
	private String to3;
	@Column(length = 20)
	private String to4;
	@Column(length = 20)
	private String to5;

	private Long catid;
}
