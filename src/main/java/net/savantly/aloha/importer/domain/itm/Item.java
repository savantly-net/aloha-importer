package net.savantly.aloha.importer.domain.itm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.savantly.aloha.importer.dbf.records.ChecksForExistingRecord;
import net.savantly.aloha.importer.dbf.records.ExistingRecordStrategy;

@Data
@Entity
@EqualsAndHashCode(exclude = {"posKey", "importId", "importDate"})
public class Item implements ChecksForExistingRecord<Long> {
	
	@Override
	public ExistingRecordStrategy getExistingRecordStrategy() {
		return ExistingRecordStrategy.SKIP_IF_EQUAL;
	}
	@Override
	public Long getUniqueRecordIdentifier() {
		return id;
	}

	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 15)
	private String shortname;
	@Column(length = 15)
	private String chitname;
	@Column(length = 25)
	private String longname;
	@Column(length = 25)
	private String longname2;
	@Column(length = 20)
	private String bohname;
	@Column(length = 4)
	private String abbrev;

	private BigDecimal taxid;

	private BigDecimal taxid2;

	private BigDecimal vtaxid;

	private Long priority;

	private Long routing;
	@Column(length = 1)
	private String printonchk;
	@Column(length = 1)
	private String combine;
	@Column(length = 1)
	private String highlight;

	private BigDecimal surcharge;
	@Column(length = 1)
	private String surchrgmod;

	private BigDecimal cost;

	private Long mod1;

	private Long mod2;

	private Long mod3;

	private Long mod4;

	private Long mod5;

	private Long mod6;

	private Long mod7;

	private Long mod8;

	private Long mod9;

	private Long mod10;
	@Column(length = 1)
	private String askdesc;
	@Column(length = 1)
	private String askprice;
	@Column(length = 1)
	private String isrefill;

	private Long vrouting;

	private Long is_kvi;
	@Column(length = 1)
	private String trackfoh;

	private BigDecimal price_id;

	private BigDecimal price;

	private Long slavetoitm;

	private Long defweight;
	@Column(length = 15)
	private String sku;
	@Column(length = 1)
	private String modprice;

	private Long itemmult;

	private Long itempercnt;

	private BigDecimal modpriceid;

	private BigDecimal mod_price;

	private Long ampri;
	@Column(length = 1)
	private String neverprint;

	private Long prtrecipe;
	@Column(length = 1)
	private String nogratuity;

	private Long compositid;
	@Column(length = 1)
	private String adisprecp;
	@Column(length = 1)
	private String independnt;
	@Column(length = 1)
	private String printheld;
	@Column(length = 1)
	private String donotshow;
	@Column(length = 1)
	private String donotshwm;
	@Column(length = 1)
	private String showndpnt;
	@Column(length = 1)
	private String giftcert;
	@Column(length = 1)
	private String itemhilite;

	private Long delaytime;

	private Long label;

	private Long guests;

	private Long siteflags;

	private BigDecimal flextax;
	@Column(length = 1)
	private String cashcard;

	private Long concept;

	private Long multiplier;
	@Column(length = 15)
	private String sku2;
	@Column(length = 15)
	private String sku3;
	@Column(length = 15)
	private String sku4;
	@Column(length = 15)
	private String sku5;
	@Column(length = 1)
	private String revitem;
	@Column(length = 1)
	private String con1stmod;

	private Long chitjust;
	@Column(length = 1)
	private String chitbold;

	private BigDecimal flextax2;
	@Column(length = 1)
	private String dntshwsm;
	@Column(length = 1)
	private String swtrcksm;
	@Column(length = 1)
	private String giftcard;
	@Column(length = 1)
	private String gcactivate;
	@Column(length = 1)
	private String gcaddvalue;
	@Column(length = 1)
	private String tokenover;

	private Long tokenqty;

	private Long guestwght;
	@Column(length = 1)
	private String dntshwsmmd;
	@Column(length = 1)
	private String usebkclr;

	private Long bkred;

	private Long bkgreen;

	private Long bkblue;
	@Column(length = 1)
	private String usetxtclr;

	private Long txtred;

	private Long txtgreen;

	private Long txtblue;
	@Column(length = 1)
	private String dispbmp;
	@Column(length = 12)
	private String bitmapfile;
	@Column(length = 1)
	private String hidetxt;
	@Column(length = 15)
	private String chitname2;

	private Long ctxpnlid;
	@Column(length = 1)
	private String mdspctxpnl;
	@Column(length = 1)
	private String pizza;
	@Column(length = 1)
	private String topping;

	private Long inittop;

	private Long pizzasize;
	@Column(length = 1)
	private String mustbwhole;
	@Column(length = 1)
	private String fraction;

	private Long fractype;

	private Long fracprcovr;

	private Long type;
	@Column(length = 1)
	private String fsitem;

	private Long fsmatrix;

	private Long exportid;

	private Long camprovide;

	private Long planindex;

	private Long camaction;

	private BigDecimal occtax;

}