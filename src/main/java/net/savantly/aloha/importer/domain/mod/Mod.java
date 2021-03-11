package net.savantly.aloha.importer.domain.mod;

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
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@EqualsAndHashCode(exclude = {"importId", "importDate"})
@Entity
@IdClass(ModId.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mod implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 15)
	private String shortname;
	@Column(length = 40)
	private String longname;

	private Long minimum;

	private Long maximum;

	private Long free;
	@Column(length = 1)
	private String flowreq;

	private Long substitute;

	private Long item01;

	private Long item02;

	private Long item03;

	private Long item04;

	private Long item05;

	private Long item06;

	private Long item07;

	private Long item08;

	private Long item09;

	private Long item10;

	private Long item11;

	private Long item12;

	private Long item13;

	private Long item14;

	private Long item15;

	private Long item16;

	private Long item17;

	private Long item18;

	private Long item19;

	private Long item20;

	private Long item21;

	private Long item22;

	private Long item23;

	private Long item24;

	private Long item25;

	private Long item26;

	private Long item27;

	private Long item28;

	private Long item29;

	private Long item30;

	private Long item31;

	private Long item32;

	private Long item33;

	private Long item34;

	private Long item35;

	private Long item36;

	private Long item37;

	private Long item38;

	private Long item39;

	private Long item40;

	private Long item41;

	private Long item42;

	private Long item43;

	private Long item44;

	private Long item45;

	private Long item46;

	private Long item47;

	private Long item48;

	private Long item49;

	private Long item50;

	private Long item51;

	private Long item52;

	private Long item53;

	private Long item54;

	private BigDecimal price01;

	private BigDecimal price02;

	private BigDecimal price03;

	private BigDecimal price04;

	private BigDecimal price05;

	private BigDecimal price06;

	private BigDecimal price07;

	private BigDecimal price08;

	private BigDecimal price09;

	private BigDecimal price10;

	private BigDecimal price11;

	private BigDecimal price12;

	private BigDecimal price13;

	private BigDecimal price14;

	private BigDecimal price15;

	private BigDecimal price16;

	private BigDecimal price17;

	private BigDecimal price18;

	private BigDecimal price19;

	private BigDecimal price20;

	private BigDecimal price21;

	private BigDecimal price22;

	private BigDecimal price23;

	private BigDecimal price24;

	private BigDecimal price25;

	private BigDecimal price26;

	private BigDecimal price27;

	private BigDecimal price28;

	private BigDecimal price29;

	private BigDecimal price30;

	private BigDecimal price31;

	private BigDecimal price32;

	private BigDecimal price33;

	private BigDecimal price34;

	private BigDecimal price35;

	private BigDecimal price36;

	private BigDecimal price37;

	private BigDecimal price38;

	private BigDecimal price39;

	private BigDecimal price40;

	private BigDecimal price41;

	private BigDecimal price42;

	private BigDecimal price43;

	private BigDecimal price44;

	private BigDecimal price45;

	private BigDecimal price46;

	private BigDecimal price47;

	private BigDecimal price48;

	private BigDecimal price49;

	private BigDecimal price50;

	private BigDecimal price51;

	private BigDecimal price52;

	private BigDecimal price53;

	private BigDecimal price54;

	private BigDecimal weight01;

	private BigDecimal weight02;

	private BigDecimal weight03;

	private BigDecimal weight04;

	private BigDecimal weight05;

	private BigDecimal weight06;

	private BigDecimal weight07;

	private BigDecimal weight08;

	private BigDecimal weight09;

	private BigDecimal weight10;

	private BigDecimal weight11;

	private BigDecimal weight12;

	private BigDecimal weight13;

	private BigDecimal weight14;

	private BigDecimal weight15;

	private BigDecimal weight16;

	private BigDecimal weight17;

	private BigDecimal weight18;

	private BigDecimal weight19;

	private BigDecimal weight20;

	private BigDecimal weight21;

	private BigDecimal weight22;

	private BigDecimal weight23;

	private BigDecimal weight24;

	private BigDecimal weight25;

	private BigDecimal weight26;

	private BigDecimal weight27;

	private BigDecimal weight28;

	private BigDecimal weight29;

	private BigDecimal weight30;

	private BigDecimal weight31;

	private BigDecimal weight32;

	private BigDecimal weight33;

	private BigDecimal weight34;

	private BigDecimal weight35;

	private BigDecimal weight36;

	private BigDecimal weight37;

	private BigDecimal weight38;

	private BigDecimal weight39;

	private BigDecimal weight40;

	private BigDecimal weight41;

	private BigDecimal weight42;

	private BigDecimal weight43;

	private BigDecimal weight44;

	private BigDecimal weight45;

	private BigDecimal weight46;

	private BigDecimal weight47;

	private BigDecimal weight48;

	private BigDecimal weight49;

	private BigDecimal weight50;

	private BigDecimal weight51;

	private BigDecimal weight52;

	private BigDecimal weight53;

	private BigDecimal weight54;

	private BigDecimal pricelvl01;

	private BigDecimal pricelvl02;

	private BigDecimal pricelvl03;

	private BigDecimal pricelvl04;

	private BigDecimal pricelvl05;

	private BigDecimal pricelvl06;

	private BigDecimal pricelvl07;

	private BigDecimal pricelvl08;

	private BigDecimal pricelvl09;

	private BigDecimal pricelvl10;

	private BigDecimal pricelvl11;

	private BigDecimal pricelvl12;

	private BigDecimal pricelvl13;

	private BigDecimal pricelvl14;

	private BigDecimal pricelvl15;

	private BigDecimal pricelvl16;

	private BigDecimal pricelvl17;

	private BigDecimal pricelvl18;

	private BigDecimal pricelvl19;

	private BigDecimal pricelvl20;

	private BigDecimal pricelvl21;

	private BigDecimal pricelvl22;

	private BigDecimal pricelvl23;

	private BigDecimal pricelvl24;

	private BigDecimal pricelvl25;

	private BigDecimal pricelvl26;

	private BigDecimal pricelvl27;

	private BigDecimal pricelvl28;

	private BigDecimal pricelvl29;

	private BigDecimal pricelvl30;

	private BigDecimal pricelvl31;

	private BigDecimal pricelvl32;

	private BigDecimal pricelvl33;

	private BigDecimal pricelvl34;

	private BigDecimal pricelvl35;

	private BigDecimal pricelvl36;

	private BigDecimal pricelvl37;

	private BigDecimal pricelvl38;

	private BigDecimal pricelvl39;

	private BigDecimal pricelvl40;

	private BigDecimal pricelvl41;

	private BigDecimal pricelvl42;

	private BigDecimal pricelvl43;

	private BigDecimal pricelvl44;

	private BigDecimal pricelvl45;

	private BigDecimal pricelvl46;

	private BigDecimal pricelvl47;

	private BigDecimal pricelvl48;

	private BigDecimal pricelvl49;

	private BigDecimal pricelvl50;

	private BigDecimal pricelvl51;

	private BigDecimal pricelvl52;

	private BigDecimal pricelvl53;

	private BigDecimal pricelvl54;

	private Long prmethod01;

	private Long prmethod02;

	private Long prmethod03;

	private Long prmethod04;

	private Long prmethod05;

	private Long prmethod06;

	private Long prmethod07;

	private Long prmethod08;

	private Long prmethod09;

	private Long prmethod10;

	private Long prmethod11;

	private Long prmethod12;

	private Long prmethod13;

	private Long prmethod14;

	private Long prmethod15;

	private Long prmethod16;

	private Long prmethod17;

	private Long prmethod18;

	private Long prmethod19;

	private Long prmethod20;

	private Long prmethod21;

	private Long prmethod22;

	private Long prmethod23;

	private Long prmethod24;

	private Long prmethod25;

	private Long prmethod26;

	private Long prmethod27;

	private Long prmethod28;

	private Long prmethod29;

	private Long prmethod30;

	private Long prmethod31;

	private Long prmethod32;

	private Long prmethod33;

	private Long prmethod34;

	private Long prmethod35;

	private Long prmethod36;

	private Long prmethod37;

	private Long prmethod38;

	private Long prmethod39;

	private Long prmethod40;

	private Long prmethod41;

	private Long prmethod42;

	private Long prmethod43;

	private Long prmethod44;

	private Long prmethod45;

	private Long prmethod46;

	private Long prmethod47;

	private Long prmethod48;

	private Long prmethod49;

	private Long prmethod50;

	private Long prmethod51;

	private Long prmethod52;

	private Long prmethod53;

	private Long prmethod54;
	@Column(length = 1)
	private String noflowbar;

	private Long subindex;
	@Column(length = 1)
	private String usemodpnl;
	@Column(length = 1)
	private String keypad;

	private Long modpnl;

	private Long qstsmode;
	@Column(length = 1)
	private String refill;
	@Column(length = 3)
	private String refillind;

	private Long maxrefills;
	@Column(length = 1)
	private String exqfrmflow;
	@Column(length = 1)
	private String defrdmod;
	@Column(length = 1)
	private String toggle;

	private Long nosub01;

	private Long nosub02;

	private Long nosub03;

	private Long nosub04;

	private Long nosub05;

	private Long nosub06;

	private Long nosub07;

	private Long nosub08;

	private Long nosub09;

	private Long nosub10;

	private Long nosub11;

	private Long nosub12;

	private Long nosub13;

	private Long nosub14;

	private Long nosub15;

	private Long nosub16;

	private Long nosub17;

	private Long nosub18;

	private Long nosub19;

	private Long nosub20;

	private Long nosub21;

	private Long nosub22;

	private Long nosub23;

	private Long nosub24;

	private Long nosub25;

	private Long nosub26;

	private Long nosub27;

	private Long nosub28;

	private Long nosub29;

	private Long nosub30;

	private Long nosub31;

	private Long nosub32;

	private Long nosub33;

	private Long nosub34;

	private Long nosub35;

	private Long nosub36;

	private Long nosub37;

	private Long nosub38;

	private Long nosub39;

	private Long nosub40;

	private Long nosub41;

	private Long nosub42;

	private Long nosub43;

	private Long nosub44;

	private Long nosub45;

	private Long nosub46;

	private Long nosub47;

	private Long nosub48;

	private Long nosub49;

	private Long nosub50;

	private Long nosub51;

	private Long nosub52;

	private Long nosub53;

	private Long nosub54;
	@Column(length = 1)
	private String autorepinc;

	private Long type;

	private Long modgrppnl;
	@Column(length = 1)
	private String remspclmes;
	@Column(length = 1)
	private String showmodcd;
	@Column(length = 1)
	private String hideprice;
	@Column(length = 1)
	private String suppmodscr;

}