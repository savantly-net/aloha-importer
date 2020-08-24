package net.savantly.aloha.importer.domain.common;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImportProcessingRequest {

	private InputStream input;
	private Long posKey;
	private String importFileName;
	
}
