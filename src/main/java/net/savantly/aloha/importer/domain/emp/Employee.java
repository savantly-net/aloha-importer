package net.savantly.aloha.importer.domain.emp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

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
@IdClass(EmployeeId.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;

	private Long sec_num;

	@Transient
	private Long ssn;

	@Transient
	@Column(length = 11)
	private String ssntext;
	
	@Column(length = 30)
	private String firstname;
	@Column(length = 30)
	private String middlename;
	@Column(length = 30)
	private String lastname;
	@Column(length = 30)
	private String nickname;
	@Column(length = 30)
	private String address1;
	@Column(length = 25)
	private String address2;
	@Column(length = 20)
	private String city;
	@Column(length = 15)
	private String state;
	@Column(length = 25)
	private String zipcode;
	@Column(length = 26)
	private String phone;
	@Column(length = 25)
	private String country;

	private Long countrycde;

	private Long localeid;

	private Date birthday;

	private Date dateofhire;

	private Date lastaccess;

	@Transient
	@Column(length = 25)
	private String password;
	
	@Transient
	@Column(length = 25)
	private String magcard;

	private Long security;

	private Long tips;
	
	@Transient
	@Column(length = 1)
	private String qwerty;

	private Long wktotmin;

	private Long wktovmin;

	private Long wkdovmin;

	private Long wktotpay;

	private Long wktovpay;

	private Long wkdovpay;

	private Long jobcode1;

	private Long jobcode2;

	private Long jobcode3;

	private Long jobcode4;

	private Long jobcode5;

	private Long jobcode6;

	private Long jobcode7;

	private Long jobcode8;

	private Long jobcode9;

	private Long jobcode10;

	private Long payrate1;

	private Long payrate2;

	private Long payrate3;

	private Long payrate4;

	private Long payrate5;

	private Long payrate6;

	private Long payrate7;

	private Long payrate8;

	private Long payrate9;

	private Long payrate10;

	private Long access1;

	private Long access2;

	private Long access3;

	private Long access4;

	private Long access5;

	private Long access6;

	private Long access7;

	private Long access8;

	private Long access9;

	private Long access10;

	private Long skill1;

	private Long skill2;

	private Long skill3;

	private Long skill4;

	private Long skill5;

	private Long skill6;

	private Long skill7;

	private Long skill8;

	private Long skill9;

	private Long skill10;

	private Long pref1;

	private Long pref2;

	private Long pref3;

	private Long pref4;

	private Long pref5;

	private Long pref6;

	private Long pref7;

	private Long pref8;

	private Long pref9;

	private Long pref10;
	
	@Column(length = 1)
	private String meals;

	private Long mealpcnt;
	@Column(length = 1)
	private String terminated;

	private Long zapid;
	@Column(length = 1)
	private String rehire;

	private Date lastday;

	private Date rtnday;
	@Column(length = 40)
	private String zapexpln;

	private Long xferunit;
	@Column(length = 1)
	private String move;

	private Long marital;

	private Long numdepend;
	@Column(length = 1)
	private String exempt;

	private Long withextra;

	private Long vetranstat;
	@Column(length = 1)
	private String magonly;

	private Long ddlrfee;

	private Long dprcntfee;

	private Long dmlgfee;

	private Date ddlexp;

	private Date dinsrncexp;
	@Column(length = 1)
	private String sex;

	private Long jobstatus;
	@Column(length = 20)
	private String empcode1;
	@Column(length = 20)
	private String empcode2;

	@Transient
	@Column(length = 25)
	private String bohpasswrd;

	private Long seclevel;
	@Column(length = 6)
	private String starttime;
	@Column(length = 6)
	private String endtime;

	private Date pwdchange;

	private Long penid;

	private Long teamsort;
	@Column(length = 1)
	private String teamlmtrev;
	@Column(length = 25)
	private String address3;
	@Column(length = 25)
	private String address4;
	@Column(length = 25)
	private String county;
	@Column(length = 1)
	private String thumbscci;

	private Long workpolid;

	private Long emptypeid;
	@Column(length = 1)
	private String thumbscli;
	@Column(length = 50)
	private String remarks;

	private Date schedstart;

	private Date schedend;
	@Column(length = 20)
	private String empcode3;
	@Column(length = 20)
	private String empcode4;
	@Column(length = 20)
	private String empcode5;
	@Column(length = 1)
	private String waivembrk;

	private Long lastchpswd;

	private Long bohlastpwd;

	private Long ncflogon;

	@Transient
	@Column(length = 64)
	private String ssn_enc;
	
	@Column(length = 20)
	private String empliqcert;

	private Date empliqexp;

	private Long minorxmt;

	@Transient
	@Column(length = 170)
	private String bohhashpw;
	
	@Column(length = 9)
	private String exp_id;
	@Column(length = 1)
	private String dlskeyonly;
	

	@Transient
	@Column(length = 25)
	private String dlskeypwd;
	

	@Transient
	@Column(length = 170)
	private String fohhashpw;

	@Transient
	@Column(length = 170)
	private String magcrdhash;

	@Transient
	@Column(length = 170)
	private String dlskeyhash;

	@Transient
	@Column(length = 41)
	private String loyalty;

}