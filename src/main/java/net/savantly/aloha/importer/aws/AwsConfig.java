package net.savantly.aloha.importer.aws;

import java.util.Collections;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.savantly.aloha.importer.aws.s3.BucketDigester;
import net.savantly.aloha.importer.aws.s3.InboundS3EventHandler;
import net.savantly.aloha.importer.aws.s3.S3FilenameUtil;
import net.savantly.aloha.importer.dbf.ImporterBeanResolver;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * 
 * This code expects that you have AWS credentials set up in the properties file
 */
@Configuration
@Getter @Setter
@RequiredArgsConstructor
public class AwsConfig {
	 
	private final static Logger log = LoggerFactory.getLogger(AwsConfig.class);
	private final AwsConfigProperties props;
 
    @Bean
    public S3Client getAmazonS3Cient() {
        return S3Client.builder().region(Region.of(props.getRegion()))
        	.credentialsProvider(new AwsCredentialsProvider() {
				@Override
				public AwsCredentials resolveCredentials() {
					return AwsBasicCredentials.create(props.getAccessKey(), props.getSecretKey());
				}
			})
        	.build();
    }
    
    @Bean
    public S3FilenameUtil s3FilenameUtil() {
    	return new S3FilenameUtil(props);
    }
    
    @ConditionalOnProperty("aws.s3.digester.enabled")
    @Bean(name = BucketDigester.BEAN_NAME)
    public BucketDigester bucketDigester(S3Client s3, ImporterBeanResolver importerBeanResolver, ResourceLoader resourceLoader, DataSource datasource, S3FilenameUtil s3FileNameUtil) {
    	return new BucketDigester(props, s3, importerBeanResolver, resourceLoader, datasource, s3FileNameUtil);
    }

    @ConditionalOnProperty("aws.sqs.enabled")
    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory() {
    	QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
    	MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();

    	//set strict content type match to false
    	messageConverter.setStrictContentTypeMatch(false);
    	factory.setArgumentResolvers(Collections.<HandlerMethodArgumentResolver>singletonList(new PayloadArgumentResolver(messageConverter)));
    	return factory;
    }

    @ConditionalOnProperty("aws.sqs.enabled")
    @Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}
    
    public AmazonSQSAsync amazonSQSAsync() {

		AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder = AmazonSQSAsyncClientBuilder.standard();
		AmazonSQSAsync amazonSQSAsync = null;
		amazonSQSAsyncClientBuilder.withRegion(props.getRegion());
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey());
		amazonSQSAsyncClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));
		amazonSQSAsync = amazonSQSAsyncClientBuilder.build();
		
		String configuredInbound = props.getSqs().getInboundQueue();
		if (Objects.isNull(configuredInbound)) {
			throw new RuntimeException("When aws.sqs.enabled = true, the inbound aws.sqs.inboundQueue must be set");
		}
		if (configuredInbound.startsWith("http")) {
			log.info("Using inbound queue {}", configuredInbound);
		} else {
			try {
				GetQueueUrlResult qUrlResult = amazonSQSAsync.getQueueUrl(configuredInbound);
				log.info("Using inbound queue: {}", qUrlResult.getQueueUrl());
			} catch (QueueDoesNotExistException ex) {
				log.warn("Queue does not exist: {}", configuredInbound);
				log.info("Creating queue: {}", configuredInbound);
				try {
					CreateQueueResult createQueue = amazonSQSAsync.createQueue(configuredInbound);
					log.info("Created queue: {}", createQueue);
					log.info("Using inbound queue: {}", createQueue.getQueueUrl());
				} catch (AmazonSQSException exCreate) {
					log.error("failed to create queue: {}", exCreate);
				}
			}
		}
		
		return amazonSQSAsync;

	}

    @ConditionalOnProperty("aws.sqs.enabled")
    @Bean
    public InboundS3EventHandler inboundS3EventHandler(
    		S3FilenameUtil s3FileNameUtil, S3Client s3, ImporterBeanResolver importerResolver, QueueMessagingTemplate messageTemplate) {
    	return new InboundS3EventHandler(props.getS3().getBucketName(), s3FileNameUtil, s3, importerResolver, messageTemplate, props.getSqs().getOutboundQueue());
    }
    
}
