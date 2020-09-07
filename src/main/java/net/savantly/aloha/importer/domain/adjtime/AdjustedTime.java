package net.savantly.aloha.importer.domain.adjtime;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

/**
 * SSN and password fields are marked transient to keep from storing in DB.
 * If there is a need to store this data, maybe some encryption options could be created
 * @author jeremy branham
 *
 */
@Data
@Entity
@Table(indexes = { @Index(columnList = "date", unique = false) })
public class AdjustedTime implements ImportIdentifiable {

	@Id
	@GeneratedValue
	private Long recordId;

	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long employee;

	private Long shift_id;
	@Column(length = 1)
	private String invalid;

	@Transient
	private Long ssn;

	private Date date;

	private Date sysdatein;

	private Date sysdateout;

	private Long jobcode;

	private Long inhour;

	private Long inminute;

	private Long outhour;

	private Long outminute;

	private Long minutes;

	private Long hours;

	private BigDecimal rate;

	private BigDecimal pay;

	private Long overmin;

	private Long overhrs;

	private BigDecimal overrate;

	private BigDecimal overpay;

	private Long driverfees;

	private BigDecimal cctips;

	private BigDecimal dectips;

	private BigDecimal sales;

	private BigDecimal salesin;

	private BigDecimal salesout;

	private BigDecimal notipsales;
	
	@Column(length = 20)
	private String manager;

	private Long paidbrk;

	private Long paidbrkhrs;

	private BigDecimal paidbrkpay;

	private Long unpaidbrk;

	private Long unpdbrkhrs;

	private BigDecimal unpbrkpay;
	@Column(length = 1)
	private String tipped;
	@Column(length = 1)
	private String seen;

	private Long regovmin;

	private Long regovhrs;

	private Long regovpay;

	private Long dayovmin;

	private Long dayovhrs;

	private Long dayovpay;

	private Long punch_id;

	private Long unit;

	private Long occasion;

	private Long startmiles;

	private Long endmiles;

	private Long dlvruns;

	private Long dlvorders;

	private BigDecimal dlvsales;

	private BigDecimal cctipsales;

	private Long vizor;

	private Long reason;

	private BigDecimal totccsls;

	private BigDecimal tnocshsls;

	private BigDecimal tnocshtip;

	private BigDecimal tiprefund;

	private BigDecimal tipshcon;

	private BigDecimal autgrttot;

	private BigDecimal grosstxin;

	private BigDecimal grosstxout;

	private BigDecimal grosssls;

	private Long noofcheck;

	private Long noofguest;

	private BigDecimal totcomp;

	private BigDecimal totpromo;

	private Long spectndr;

	private Long mgrnumber;

	private Long novtbkmn;

	private Long novtbkhr;

	private BigDecimal novtbkpy;

	private Date editdate;
	@Column(length = 8)
	private String edittime;

	private Long waivebreak;

	private Long brkviolatd;

	private Long schedulin;

	private Long schedulout;

	private Long outmgr;

	private BigDecimal tipsthresh;

	private BigDecimal tpshrrecvd;

	private BigDecimal exctips;

	private Long deccashtip;
	@Column(length = 1)
	private String coutbyeod;
	@Column(length = 1)
	private String clockedin;

	private Long cntshiftid;
	@Column(length = 1)
	private String addfromboh;

}