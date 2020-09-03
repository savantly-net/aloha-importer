package net.savantly.aloha.importer.aws;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("aws")
@Getter @Setter
public class AwsConfigProperties {
	
	/**
	 * The AWS Region
	 */
	private String region = "us-east-2";
	private S3 s3 = new S3();

	@Getter @Setter
	public static class S3 {
		private String bucketName;
		private S3Digester digester = new S3Digester();
	}

	@Getter @Setter
	public static class S3Digester {
		private boolean enabled;
		/**
		 * If an item in the bucket matches one of these patterns, it will be parsed
		 */
		private List<String> keyPatterns = Arrays.asList(
				".*CAT.*$",
				".*GNDADJACK.*$",
				".*GNDDRWR.*$",
				".*GNDITEM.*$",
				".*GNDLINE.*$",
				".*GNDREVN.*$",
				".*GNDSALE.*$",
				".*GNDSLSUM.*$",
				".*MOD.*$",
				".*MODCODE.*$");
		/**
		 * This regex pattern is applied to the S3 key to create a capture group for the table name
		 */
		private String tableNameCapturePattern = "^(.+/)*(.+)\\.(.+)$";
		/**
		 * This is the index of the capture group that holds the table name found by regex
		 */
		private int tableNameCaptureGroup = 2;

		/**
		 * This regex pattern is applied to the S3 key to create a capture group for the pos key
		 */
		private String posKeyCapturePattern = "^(\\D+/)*(\\d+){1,6}/(.+)\\.(.+)$";
		/**
		 * This is the index of the capture group that holds the pos key found by regex
		 */
		private int posKeyCaptureGroup = 2;
		
		/**
		 * Stop processing if there is an exception reading s3 object
		 */
		private boolean stopOnS3ReadException = true;
	}

}
