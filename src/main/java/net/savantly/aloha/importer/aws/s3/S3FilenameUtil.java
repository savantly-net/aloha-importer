package net.savantly.aloha.importer.aws.s3;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.savantly.aloha.importer.aws.AwsConfigProperties;
import net.savantly.aloha.importer.dbf.AlohaTable;

public class S3FilenameUtil {
	
	private static final Logger log = LoggerFactory.getLogger(S3FilenameUtil.class);

	private final Pattern tableNamePattern;
	private final Pattern posKeyPattern;
	private final int posKeyCaptureGroup;
	private final int tableNameCaptureGroup;
	private final List<String> keyPatterns;
	

	public S3FilenameUtil(AwsConfigProperties props) {
		this.posKeyPattern = Pattern.compile(props.getS3().getDigester().getPosKeyCapturePattern());
		this.tableNamePattern = Pattern.compile(props.getS3().getDigester().getTableNameCapturePattern());
		this.posKeyCaptureGroup = props.getS3().getDigester().getPosKeyCaptureGroup();
		this.tableNameCaptureGroup = props.getS3().getDigester().getTableNameCaptureGroup();
		this.keyPatterns = props.getS3().getDigester().getKeyPatterns();
	}
	

	public boolean eligibleKey(String key) {
		for (String pattern : keyPatterns) {
			if (key.matches(pattern)) {
				return true;
			}
		}
		return false;
	}


	public Long extractPosKey(String key) {
		final Matcher matcher = posKeyPattern.matcher(key);
		if (!matcher.matches()) {
			log.info("posKeyPattern failed to match: {}", key);
		}
		final String extracted = matcher.group(posKeyCaptureGroup);
		if (Objects.isNull(extracted)) {
			String msg = String.format("Failed to extract pos key from: %s using pattern: %s with capture group: %s",
					key, posKeyPattern, posKeyCaptureGroup);
			log.error(msg);
			throw new RuntimeException(String.format("failed to match for pos key: {}", matcher.toString()));
		} else {
			try {
				return Long.parseLong(extracted);
			} catch (NumberFormatException e) {
				throw new RuntimeException(
						"the extracted posKey doesn't appear to be in the correct format: " + extracted, e);
			}
		}
	}

	public AlohaTable extractTableName(String key) {
		final Matcher matcher = tableNamePattern.matcher(key);
		if (!matcher.matches()) {
			log.info("tableNamePattern failed to match: {}", key);
		}
		final String extracted = matcher.group(tableNameCaptureGroup);
		if (Objects.isNull(extracted)) {
			String msg = String.format("Failed to extract name from: %s using pattern: %s with capture group: %s", key,
					tableNamePattern, tableNameCaptureGroup);
			log.error(msg);
			throw new RuntimeException(String.format("failed to match for table name: {}", matcher.toString()));
		} else {
			try {
				AlohaTable table = AlohaTable.valueOf(extracted.toUpperCase());
				return table;
			} catch (Exception e) {
				throw new RuntimeException(
						"extracted table name doesn't match an AlohaTable value. extracted: " + extracted, e);
			}
		}
	}

}
