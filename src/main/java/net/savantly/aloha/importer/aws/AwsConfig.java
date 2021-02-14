package net.savantly.aloha.importer.aws;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.savantly.aloha.importer.aws.s3.BucketDigester;
import net.savantly.aloha.importer.dbf.ImporterBeanResolver;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * 
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
@Configuration
@Getter @Setter
@RequiredArgsConstructor
public class AwsConfig {
	 
	private final AwsConfigProperties props;
 
    @Bean
    public S3Client getAmazonS3Cient() {
        return S3Client.builder().region(Region.of(props.getRegion())).build();
    }
    
    @ConditionalOnProperty("aws.s3.digester.enabled")
    @Bean(name = BucketDigester.BEAN_NAME)
    public BucketDigester bucketDigester(S3Client s3, ImporterBeanResolver importerBeanResolver, ResourceLoader resourceLoader, DataSource datasource) {
    	return new BucketDigester(props, s3, importerBeanResolver, resourceLoader, datasource);
    }
}
