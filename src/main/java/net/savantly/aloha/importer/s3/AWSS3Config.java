package net.savantly.aloha.importer.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@ConfigurationProperties("aws")
@Getter @Setter
public class AWSS3Config {
	
	private String region;
 
	/**
	 * 
	 * This code expects that you have AWS credentials set up per:
	 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
	 */
 
    @Bean
    public S3Client getAmazonS3Cient() {
        return S3Client.builder().region(Region.of(this.getRegion())).build();
    }
}