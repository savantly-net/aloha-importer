package net.savantly.aloha.importer.domain.cmp;

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
@IdClass(CompId.class)
public class Comp implements ImportIdentifiable {

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
	@Column(length = 20)
	private String name;
	@Column(length = 1)
	private String selitems;
	@Column(length = 1)
	private String enteramt;
	@Column(length = 1)
	private String enterunit;
	@Column(length = 1)
	private String entername;
	@Column(length = 1)
	private String namemgcrd;
	@Column(length = 1)
	private String mgrneeded;
	@Column(length = 1)
	private String printmgr;
	@Column(length = 1)
	private String printcheck;

	private BigDecimal maxamount;

	private Long rate;

	private Long buttonpos;

	private Long itemcat;

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
	private String taxable2;
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
	@Column(length = 20)
	private String checkname;
	@Column(length = 1)
	private String enterrate;
	@Column(length = 1)
	private String opendrawer;

	private Long verifytype;
	@Column(length = 1)
	private String firstpos;
	@Column(length = 1)
	private String applygrat;

	private Long minpct;

	private Long maxpct;
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
	private String efreqcomp;
	@Column(length = 1)
	private String costpct;

	private BigDecimal costpctmkp;
	@Column(length = 1)
	private String posttopms;
	@Column(length = 1)
	private String pmsoffline;
	@Column(length = 5)
	private String cardprefix;
	@Column(length = 10)
	private String pmsaccount;
	@Column(length = 1)
	private String prtvoucher;
	@Column(length = 1)
	private String txtreq;
	@Column(length = 20)
	private String lowcode;
	@Column(length = 20)
	private String highcode;
	@Column(length = 1)
	private String zerobal;

}