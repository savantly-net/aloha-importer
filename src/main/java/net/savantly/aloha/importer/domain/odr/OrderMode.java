package net.savantly.aloha.importer.domain.odr;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.savantly.aloha.importer.dbf.records.ChecksForExistingRecord;
import net.savantly.aloha.importer.dbf.records.ExistingRecordStrategy;

@Data
@Entity
@IdClass(OrderModeId.class)
@EqualsAndHashCode(exclude = {"importId", "importDate"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderMode implements ChecksForExistingRecord<OrderModeId> {
	
	@Override
	public ExistingRecordStrategy getExistingRecordStrategy() {
		return ExistingRecordStrategy.SKIP_IF_EQUAL;
	}
	
	@Override
	public OrderModeId getUniqueRecordIdentifier() {
		return new OrderModeId().setId(id).setPosKey(posKey);
	}

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 12)
	private String name;
	@Column(length = 3)
	private String indicator;
	@Column(length = 20)
	private String header;
	@Column(length = 20)
	private String trailer;

	private Long routing;

	private int taxid;

	private int taxid2;
	@Column(length = 1)
	private String active;
	@Column(length = 1)
	private String allitems;
	@Column(length = 1)
	private String protaxover;
	@Column(length = 1)
	private String cmptaxover;
	@Column(length = 1)
	private String include;

	private Long items;
	@Column(length = 1)
	private String tippable;

	private BigDecimal charge;
	@Column(length = 1)
	private String addcharge;
	@Column(length = 1)
	private String delivery;

	private Long vrouting;
	@Column(length = 1)
	private String nontaxable;

	private int taxcat;
	@Column(length = 1)
	private String round5;
	@Column(length = 1)
	private String incintot;
	@Column(length = 1)
	private String printname;
	@Column(length = 1)
	private String pphonenum;
	@Column(length = 1)
	private String pshortadd;
	@Column(length = 1)
	private String plongadd;
	@Column(length = 1)
	private String pdirection;
	@Column(length = 1)
	private String printuser1;
	@Column(length = 1)
	private String printuser2;

	private Long infolocat;

	private Long vouchprtid;

	private Long ovexpprtid;

	private Long revctrid;

	private int chargtaxid;
	@Column(length = 20)
	private String chrglntxt;
	@Column(length = 1)
	private String enablechrg;
	@Column(length = 1)
	private String exaddlchrg;
	@Column(length = 1)
	private String xpobarcode;
	@Column(length = 1)
	private String orderedtms;
	@Column(length = 1)
	private String exrmenucat;
	@Column(length = 1)
	private String inclprcnet;

	private Long minimum;

	private Long maximum;

	private Long cshthrshld;
	@Column(length = 1)
	private String donotsend;

	private int chrgtaxid2;

	private Long category1;

	private int taxtype2;

	private Long category2;
	@Column(length = 1)
	private String dspkiosk;

}