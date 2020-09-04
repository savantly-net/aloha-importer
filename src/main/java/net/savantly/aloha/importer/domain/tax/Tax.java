package net.savantly.aloha.importer.domain.tax;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@IdClass(TaxId.class)
@EqualsAndHashCode(exclude = { "importId", "importDate" })
public class Tax implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 15)
	private String name;

	private Long substitute;
	@Column(length = 1)
	private String exclusive;
	@Column(length = 1)
	private String inclusive;
	@Column(length = 1)
	private String vendor;

	private BigDecimal rate;

	private Long norepeat01;

	private Long norepeat02;

	private Long norepeat03;

	private Long norepeat04;

	private Long norepeat05;

	private Long norepeat06;

	private Long norepeat07;

	private Long norepeat08;

	private Long norepeat09;

	private Long norepeat10;

	private Long norepeat11;

	private Long norepeat12;

	private Long norepeat13;

	private Long norepeat14;

	private Long norepeat15;

	private Long norepeat16;

	private Long norepeat17;

	private Long norepeat18;

	private Long norepeat19;

	private Long norepeat20;

	private Long norepeat21;

	private Long norepeat22;

	private Long norepeat23;

	private Long norepeat24;

	private Long norepeat25;

	private Long norepeat26;

	private Long norepeat27;

	private Long norepeat28;

	private Long norepeat29;

	private Long norepeat30;

	private Long norepeat31;

	private Long norepeat32;

	private Long norepeat33;

	private Long norepeat34;

	private Long norepeat35;

	private Long norepeat36;

	private Long norepeat37;

	private Long norepeat38;

	private Long norepeat39;

	private Long norepeat40;

	private Long norepeat41;

	private Long norepeat42;

	private Long norepeat43;

	private Long norepeat44;

	private Long norepeat45;

	private Long norepeat46;

	private Long norepeat47;

	private Long norepeat48;

	private Long norepeat49;

	private Long norepeat50;

	private Long repeat01;

	private Long repeat02;

	private Long repeat03;

	private Long repeat04;

	private Long repeat05;

	private Long repeat06;

	private Long repeat07;

	private Long repeat08;

	private Long repeat09;

	private Long repeat10;

	private Long repeat11;

	private Long repeat12;

	private Long repeat13;

	private Long repeat14;

	private Long repeat15;

	private Long repeat16;

	private Long repeat17;

	private Long repeat18;

	private Long repeat19;

	private Long repeat20;

	private Long repeat21;

	private Long repeat22;

	private Long repeat23;

	private Long repeat24;

	private Long repeat25;

	private Long repeat26;

	private Long repeat27;

	private Long repeat28;

	private Long repeat29;

	private Long repeat30;

	private Long repeat31;

	private Long repeat32;

	private Long repeat33;

	private Long repeat34;

	private Long repeat35;

	private Long repeat36;

	private Long repeat37;

	private Long repeat38;

	private Long repeat39;

	private Long repeat40;

	private Long repeat41;

	private Long repeat42;

	private Long repeat43;

	private Long repeat44;

	private Long repeat45;

	private Long repeat46;

	private Long repeat47;

	private Long repeat48;

	private Long repeat49;

	private Long repeat50;
	@Column(length = 1)
	private String secondary;

	private BigDecimal mintaxable;

	private BigDecimal smarttax;

	private Long artaxid;
	@Column(length = 1)
	private String gst;
	@Column(length = 1)
	private String taxexempt;

	private Long pmstaxid;

	private BigDecimal maxtax;
	@Column(length = 1)
	private String occupation;
	@Column(length = 1)
	private String calcexempt;

}