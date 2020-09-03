package net.savantly.aloha.importer.domain.gndadjck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@Table(indexes = {@Index(columnList="posKey", unique = false)})
public class GndAdjAck implements ImportIdentifiable {
	
	@Id
	@GeneratedValue
	private Long recordId;

	private Long posKey;
	private Long importId;
	private Date importDate;

	private Long timeofedit;

	private Date dob;

	private Long employee;

	private Date orgdatein;

	private Date orgdateout;

	private Long shiftid;

	private Long orgjobcode;

	private Long orginhr;

	private Long orginmin;

	private Long orgouthr;

	private Long orgoutmin;

	private Long breakid;

	private Long orgbkinhr;

	private Long orgbkinmin;

	private Long orgbkouthr;

	private Long orgbkoutm;

	private Long orgbrkpaid;

	private Long adjjobcode;

	private Date adjdatein;

	private Date adjdateout;

	private Long adjinhr;

	private Long adjinmin;

	private Long adjouthr;

	private Long adjoutmin;

	private Long adjbkinhr;

	private Long adjbkinmin;

	private Long adjbkouth;

	private Long adjbkoutm;

	private Long adjbrkpaid;

	private Long adjmgrid;
	@Column(length = 20)
	private String adjmgrname;

	private Long reason;

	private Long adjack;

	private Long adackmgrid;
	@Column(length = 20)
	private String adjackmgr;
	@Column(length = 1)
	private String shiftadd;
	@Column(length = 1)
	private String shiftdel;
	@Column(length = 1)
	private String brkadd;
	@Column(length = 1)
	private String brkdel;

	private Long brkreason;

	private Long orgsales;

	private Long adjsales;

	private Long orgcctips;

	private Long adjcctips;

	private Long orggrat;

	private Long adjgrat;

	private Long orgwgegrat;

	private Long adjwgegrat;
	@Column(length = 1)
	private String hasedtsale;
	@Column(length = 1)
	private String hasedtpunc;

	private Long timeofack;

}