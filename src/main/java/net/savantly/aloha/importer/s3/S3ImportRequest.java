package net.savantly.aloha.importer.s3;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class S3ImportRequest {

	private Long posKey;
	private String bucket;
	private String fileKey;
}
