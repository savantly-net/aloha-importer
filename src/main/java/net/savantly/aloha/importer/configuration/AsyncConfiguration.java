package net.savantly.aloha.importer.configuration;

import java.util.concurrent.Executor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("async")
@Setter
@Getter
public class AsyncConfiguration {
	
	private int corePoolSize = 2;
	private int maxPoolSize = 2;
	private int queueCapacity = 100;
	private String threadNamePrefix = "AlohaImport-";

	@Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
