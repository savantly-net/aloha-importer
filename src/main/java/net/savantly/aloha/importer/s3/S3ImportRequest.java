package net.savantly.aloha.importer.s3;

import lombok.Getter;
import lombok.Setter;
import net.savantly.aloha.importer.dbf.AlohaTable;

@Getter @Setter
public class S3ImportRequest {

	private Long posKey;
	private String bucket;
	private String fileKey;
	private AlohaTable table;
}
