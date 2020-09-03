package net.savantly.aloha.importer.domain.gnddrwr;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import net.savantly.aloha.importer.dbf.ImportIdentifiable;

@Data
@Entity
@Table(indexes = { @Index(columnList = "posKey", unique = false) })
public class GndDrwr implements ImportIdentifiable {

	@Id
	@GeneratedValue
	private Long recordId;
	
	private Long posKey;
	private Long importId;
	private Date importDate;

	private Date dob;

	private Date sysdate;

	private Long employee;

	private Long drawer;

	private Long state;

	private Long acctime;

	private Long hour;

	private Long minute;

	private Long second;

	private Long occasion;

}