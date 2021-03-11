package net.savantly.aloha.importer.domain.cat;

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

@EqualsAndHashCode(exclude = { "importId", "importDate" })
@Data
@Entity
@IdClass(CatId.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cat implements ImportIdentifiable {

	@Id
	private Long posKey;
	private Long importId;
	private Date importDate;

	@Id
	private Long id;

	private Long ownerid;

	private Long usernumber;
	@Column(length = 20)
	private String name;
	@Column(length = 40, name = "item_desc")
	private String desc;
	@Column(length = 20)
	private String parent;
	@Column(length = 1)
	private String sales;
	@Column(length = 1)
	private String retail;
	@Column(length = 1)
	private String suggestive;
	@Column(length = 1)
	private String tipshare;
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

	private Long pmscat;

	private Long arcat;

	private Long maxperchk;
	@Column(length = 1)
	private String maxchkset;
	@Column(length = 1)
	private String autoorder;
	@Column(length = 1)
	private String tipsales;

}