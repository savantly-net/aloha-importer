package net.savantly.aloha.importer.domain.tdr;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@IdClass(TdrId.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tdr implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;
	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 10)
	private String name;
	@Column(length = 1)
	private String cash;
	@Column(length = 1)
	private String gcert;

	private Long gcert_id;
	@Column(length = 1)
	private String rmvtipline;
	@Column(length = 1)
	private String change;

	private Long reportas;
	@Column(length = 1)
	private String combine;

	private Long buttonpos;

	private BigDecimal defaultamt;
	@Column(length = 1)
	private String track;
	@Column(length = 1)
	private String house;
	@Column(length = 1)
	private String active;
	@Column(length = 1)
	private String printchk;
	@Column(length = 1)
	private String opendrawer;
	@Column(length = 1)
	private String tips;

	private Long maxtippct;
	@Column(length = 1)
	private String expiration;
	@Column(length = 1)
	private String printexp;
	@Column(length = 1)
	private String checkexp;
	@Column(length = 1)
	private String signature;
	@Column(length = 1)
	private String justify;
	@Column(length = 1)
	private String identify;
	@Column(length = 15)
	private String identname;
	@Column(length = 1)
	private String printident;
	@Column(length = 1)
	private String identnumer;
	@Column(length = 1)
	private String validate;
	@Column(length = 1)
	private String valinclude;

	private Long idmindigit;

	private Long idmaxdigit;
	@Column(length = 8)
	private String idprefix1;
	@Column(length = 8)
	private String idprefix2;
	@Column(length = 8)
	private String idprefix3;
	@Column(length = 8)
	private String idprefix4;
	@Column(length = 1)
	private String authorize;
	@Column(length = 1)
	private String authnumer;
	@Column(length = 15)
	private String authname;
	@Column(length = 1)
	private String printauth;
	@Column(length = 1)
	private String authedc;

	private Long authlength;
	@Column(length = 20)
	private String minauth;
	@Column(length = 20)
	private String maxauth;
	@Column(length = 1)
	private String excessbal;
	@Column(length = 1)
	private String xsbalbypct;

	private Long ballimit;
	@Column(length = 1)
	private String mgroverbal;

	private Long tipredux;
	@Column(length = 1)
	private String allownr;
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
	private String preauth;

	private Long cardtype;
	@Column(length = 1)
	private String magonly;

	private Long printer;

	private Long valdigits;
	@Column(length = 1)
	private String noverify;
	@Column(length = 1)
	private String forceclose;
	@Column(length = 1)
	private String pms;
	@Column(length = 1)
	private String cstprefix;

	private Long pmstdrid;
	@Column(length = 10)
	private String pmsroom;

	private Long smartcard;

	private Long fcurrncyid;
	@Column(length = 1)
	private String accntrcvbl;
	@Column(length = 1)
	private String arverify;
	@Column(length = 1)
	private String arprntblnc;

	private Long artdrid;

	private Long artprntfld;
	@Column(length = 1)
	private String firstpos;
	@Column(length = 1)
	private String cashcard;
	@Column(length = 1)
	private String cshcrdchg;
	@Column(length = 1)
	private String issavedcci;

	private Long variance;
	@Column(length = 1)
	private String recondet;
	@Column(length = 1)
	private String autofill;
	@Column(length = 1)
	private String ischeck;
	@Column(length = 1)
	private String external;
	@Column(length = 1)
	private String debit;
	@Column(length = 1)
	private String canrefund;
	@Column(length = 1)
	private String affectdep;
	@Column(length = 1)
	private String arprtref;
	@Column(length = 1)
	private String giftcard;

	private Long gchost;
	@Column(length = 1)
	private String iscredtcrd;
	@Column(length = 1)
	private String novouchers;
	@Column(length = 1)
	private String nomaskcard;
	@Column(length = 1)
	private String alwaysdisp;
	@Column(length = 15)
	private String ivrphoneno;
	@Column(length = 1)
	private String verifysign;
	@Column(length = 1)
	private String verstdate;
	@Column(length = 1)
	private String veralldate;
	@Column(length = 20)
	private String dateprompt;
	@Column(length = 1)
	private String verseccode;
	@Column(length = 1)
	private String verallcode;

	private Long maxsecdig;
	@Column(length = 20)
	private String secprompt;
	@Column(length = 1)
	private String pmsoffline;
	@Column(length = 1)
	private String cpreaderid;
	@Column(length = 1)
	private String verifyavs;
	@Column(length = 1)
	private String verallavs;
	@Column(length = 1)
	private String avsnumeric;

	private Long avslength;
	@Column(length = 20)
	private String avsprompt;
	@Column(length = 1)
	private String autofixpay;
	@Column(length = 1)
	private String applyccsgn;
	@Column(length = 1)
	private String prtsgnline;
	@Column(length = 1)
	private String prtvoucher;
	@Column(length = 1)
	private String checkdigit;
	@Column(length = 1)
	private String vervalcode;
	@Column(length = 1)
	private String verallval;
	@Column(length = 1)
	private String valnumeric;
	@Column(length = 1)
	private String valprompt2;
	@Column(length = 20)
	private String valprompt;
	@Column(length = 1)
	private String valreqrd;
	@Column(length = 1)
	private String roundactiv;

	private Long roundto;
	@Column(length = 1)
	private String dispadjust;
	@Column(length = 1)
	private String prtind;

	private Long roundmthd;
	@Column(length = 1)
	private String gc_force;
	@Column(length = 1)
	private String idtwice;
	@Column(length = 1)
	private String ebt;

	private Long ebttype;
	@Column(length = 1)
	private String tdrnotax;

	private Long valmin;

	private Long valmax;
	@Column(length = 1)
	private String dispchange;
	@Column(length = 1)
	private String presindctr;
	@Column(length = 1)
	private String prtresp;

	private Long tdrsurch;
	@Column(length = 1)
	private String cashout;
	@Column(length = 1)
	private String disloyalty;
	@Column(length = 1)
	private String iscampus;

	private Long camprovide;

	private Long allowcat;

	private Long campuscard;

	private Long pinpadid;
	@Column(length = 1)
	private String idfldfirst;
	@Column(length = 1)
	private String cpmanual;

}