package net.savantly.aloha.importer.domain.pro;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import net.savantly.aloha.importer.dbf.records.ChecksForExistingRecord;
import net.savantly.aloha.importer.dbf.records.ExistingRecordStrategy;

@Data
@Entity
@IdClass(PromoId.class)
public class Promo implements ChecksForExistingRecord<PromoId> {

	@Override
	public ExistingRecordStrategy getExistingRecordStrategy() {
		return ExistingRecordStrategy.SKIP_IF_EQUAL;
	}
	@Override
	public PromoId getUniqueRecordIdentifier() {
		return new PromoId().setId(id).setPosKey(posKey);
	}
	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 1)
	private String active;

	private Date startdate;

	private Date enddate;
	@Column(length = 20)
	private String name;

	private Long kind;
	@Column(length = 1)
	private String mgrneeded;
	@Column(length = 1)
	private String printcheck;

	private Long buttonpos;

	private Long reportas;
	@Column(length = 1)
	private String taxable;

	private BigDecimal taxid;
	@Column(length = 1)
	private String gstpaystax;
	@Column(length = 1)
	private String gstnpyincl;
	@Column(length = 1)
	private String taxfoodcst;

	private Long foodcstpct;
	@Column(length = 1)
	private String vtaxable;
	@Column(length = 1)
	private String vtxfoodcst;

	private Long vtxfoodpct;
	@Column(length = 1)
	private String promoitems;
	@Column(length = 1)
	private String compitems;

	private Long percheck;

	private Long pertable;
	@Column(length = 1)
	private String allowppoc;
	@Column(length = 1)
	private String allowppot;
	@Column(length = 1)
	private String allowspoc;
	@Column(length = 1)
	private String allowspot;
	@Column(length = 1)
	private String allowpcoc;
	@Column(length = 1)
	private String allowpcot;
	@Column(length = 1)
	private String allowscoc;
	@Column(length = 1)
	private String allowscot;
	@Column(length = 1)
	private String surcharge;
	@Column(length = 1)
	private String firstpos;
	@Column(length = 1)
	private String noshowlist;
	@Column(length = 1)
	private String applygrat;

	private Long bogoitms;

	private Long bogomods;
	@Column(length = 1)
	private String bogoaa;
	@Column(length = 1)
	private String bogoqaa;

	private Long bogonumitm;

	private Long bogodiscat;

	private BigDecimal bogofixpri;

	private BigDecimal bogopercnt;

	private Long bogomethod;
	@Column(length = 1)
	private String bogoaaotim;

	private Long bogodisrst;
	@Column(length = 25)
	private String comboname;

	private BigDecimal comboprice;

	private BigDecimal combotaxid;

	private BigDecimal combotax2;

	private Long combomods;

	private Long combomin01;

	private Long combomin02;

	private Long combomin03;

	private Long combomin04;

	private Long combomin05;

	private Long combomin06;

	private Long combomin07;

	private Long combomin08;

	private Long combomin09;

	private Long combomin10;

	private Long combomax01;

	private Long combomax02;

	private Long combomax03;

	private Long combomax04;

	private Long combomax05;

	private Long combomax06;

	private Long combomax07;

	private Long combomax08;

	private Long combomax09;

	private Long combomax10;

	private Long combocat01;

	private Long combocat02;

	private Long combocat03;

	private Long combocat04;

	private Long combocat05;

	private Long combocat06;

	private Long combocat07;

	private Long combocat08;

	private Long combocat09;

	private Long combocat10;

	private Long cpitms;

	private Long cpmods;

	private Long cpmustitms;

	private Long cpmustcnt;
	@Column(length = 1)
	private String cppercent;

	private BigDecimal cpamount;

	private Long cplimit;

	private Long cpselitms;
	@Column(length = 1)
	private String cpdistamt;
	@Column(length = 1)
	private String auditcnt;

	private Long auditvar;

	private Long npmustitms;

	private Long npmustcnt;

	private Long npfreemods;

	private Long nplimit;

	private Long npitem01;

	private Long npitem02;

	private Long npitem03;

	private Long npitem04;

	private Long npitem05;

	private Long npitem06;

	private Long npitem07;

	private Long npitem08;

	private Long npitem09;

	private Long npitem10;

	private BigDecimal npprice01;

	private BigDecimal npprice02;

	private BigDecimal npprice03;

	private BigDecimal npprice04;

	private BigDecimal npprice05;

	private BigDecimal npprice06;

	private BigDecimal npprice07;

	private BigDecimal npprice08;

	private BigDecimal npprice09;

	private BigDecimal npprice10;
	@Column(length = 1)
	private String npautoappy;

	private Long rxitems;
	@Column(length = 1)
	private String rxqualify;

	private Long rxqualitms;

	private Long rxmintotal;
	@Column(length = 1)
	private String rxpercent;

	private BigDecimal rxamount;
	@Column(length = 1)
	private String rxautoappy;
	@Column(length = 1)
	private String taxable2;

	private Long area;

	private Long region;

	private Long store;
	@Column(length = 20)
	private String lowcode;
	@Column(length = 20)
	private String highcode;
	@Column(length = 1)
	private String user1;
	@Column(length = 1)
	private String user2;
	@Column(length = 1)
	private String user3;
	@Column(length = 1)
	private String user4;
	@Column(length = 1)
	private String user5;
	@Column(length = 1)
	private String tippable;

	private Long maximum;
	@Column(length = 1)
	private String tokenover;

	private Long tokenqty;
	@Column(length = 1)
	private String tokenguest;
	@Column(length = 1)
	private String tokenpause;
	@Column(length = 1)
	private String idrequired;
	@Column(length = 1)
	private String idnumeric;

	private Long idlength;
	@Column(length = 1)
	private String thruefreq;
	@Column(length = 1)
	private String memreqd;
	@Column(length = 1)
	private String efreqpromo;
	@Column(length = 1)
	private String autoitems;
	@Column(length = 1)
	private String incmodpr;

	private Long basediscat;

	private BigDecimal basedisprc;

	private Long exportid;
	@Column(length = 1)
	private String notdisc;
	@Column(length = 1)
	private String cpaa;
	@Column(length = 1)
	private String comboaa;
	@Column(length = 20)
	private String bogcpntnm;

	private Long bogoitms2;

	private Long bognumitm2;
	@Column(length = 20)
	private String bogcpntnm2;
	@Column(length = 20)
	private String cmptname01;
	@Column(length = 20)
	private String cmptname02;
	@Column(length = 20)
	private String cmptname03;
	@Column(length = 20)
	private String cmptname04;
	@Column(length = 20)
	private String cmptname05;
	@Column(length = 20)
	private String cmptname06;
	@Column(length = 20)
	private String cmptname07;
	@Column(length = 20)
	private String cmptname08;
	@Column(length = 20)
	private String cmptname09;
	@Column(length = 20)
	private String cmptname10;

	private Long cpmethod;

	private Long cpsrceitem;

}