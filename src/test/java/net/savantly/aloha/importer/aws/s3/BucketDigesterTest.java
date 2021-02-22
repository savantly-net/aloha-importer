package net.savantly.aloha.importer.aws.s3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.savantly.aloha.importer.stream.BreakingForEach;

public class BucketDigesterTest {
	private static final Logger log = LoggerFactory.getLogger(BucketDigesterTest.class);
	
	/* TODO Use minio to simulate s3, so we can test the real class */
	@Test
	public void testPatternDigest() {
		String dateFormat = "YYYYMMdd";
		List<String> posList = Arrays.asList("12345", "234256");
		LocalDate startDate = LocalDate.now().minusDays(7);
		LocalDate endDate = LocalDate.now();
		String prefixTemplate = "gndxfer/{posKey}/{date}/";
		
		DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder().appendPattern(dateFormat).toFormatter();
		BreakingForEach.forEach(posList.stream(), (posKey, posListBreaker) -> {
			final Stream<LocalDate> dates = startDate.datesUntil(endDate);
			BreakingForEach.forEach(dates, (date, dateBreaker) -> {
				
				String prefix = prefixTemplate.replace("{posKey}", posKey).replace("{date}",
						date.format(dateFormatter));
				log.debug("digesting prefix: {}", prefix);
			});
		});
	}

}
