package net.savantly.aloha.importer.aws;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
				".*CAT.(dbf|DBF)$",
				".*CMP.(dbf|DBF)$",
				".*EMP.(dbf|DBF)$",
				".*GIF.(dbf|DBF)$",
				".*GNDADJACK.(dbf|DBF)$",
				".*GNDDEPST.(dbf|DBF)$",
				".*GNDDRWR.(dbf|DBF)$",
				".*GNDITEM.(dbf|DBF)$",
				".*GNDLBSUM.(dbf|DBF)$",
				".*GNDLINE.(dbf|DBF)$",
				//".*GNDPETTYCASH.*$", //no importer yet
				".*GNDREVN.(dbf|DBF)$",
				".*GNDSALE.(dbf|DBF)$",
				".*GNDSLSUM.(dbf|DBF)$",
				".*GNDTNDR.(dbf|DBF)$",
				".*GNDVOID.(dbf|DBF)$",
				".*ITM.(dbf|DBF)$",
				".*MOD.(dbf|DBF)$",
				".*MODCODE.(dbf|DBF)$",
				".*ODR.(dbf|DBF)$",
				".*PET.(dbf|DBF)$",
				".*PRO.(dbf|DBF)$",
				".*TAX.(dbf|DBF)$",
				".*TDR.(dbf|DBF)$");
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
		
		/**
		 * Maximum number of keys [files] in each page of the s3 requests.  
		 * If this is increased, you will need to increase the async queue size property. <br/>{@code async.queueCapacity}
		 */
		private int maxPerPage = 50;
		
		/**
		 * Use a prefix when listing s3 bucket to limit results
		 */
		private String prefix = null;
		
		/**
		 * s3 key delimiter 
		 */
		private String delimiter = null;
		
		/**
		 * customize the props when started by a cron job
		 */
		private CronProps cronProps = new CronProps();

		/**
		 * A uri to a resource that contains a SQL script to run after a digest. 
		 */
		private String afterDigestSqlSource = "classpath:/digest/afterDigest.sql";
	}
	
	@Getter @Setter @ToString
	public static class CronProps {
		
		/**
		 * Enable the dynamic templating of the cron job, otherwise calls digest with default params
		 */
		private boolean enabled = false;
		
		/**
		 * A prefix template to be used in combination with the {@link #posKeys} list, and the dates determined by {@link #daysBack}<br/>
		 * One digest is executed for each posKey and date<br/>
		 * {posKey} and {date} are the available variables in a pseudo handlbars template string.<br/><br/>
		 * Default - "gndxfer/{posKey}/{date}/"
		 */
		private String prefixTemplate = "gndxfer/{posKey}/{date}/";
		
		/**
		 * A uri to a resource that contains a list of posKeys [one per line] to insert in the prefix template, used in combination with each date
		 */
		private String posKeyListSource = "classpath:/digest/poskeys";
		
		/**
		 * The number of days to subtract from the current date, to begin the sync
		 */
		private int daysBack = 1;
		
		/**
		 * A custom date format to use in the {@link #prefixTemplate}
		 */
		private String dateFormat = "YYYYMMdd";

	}

}
