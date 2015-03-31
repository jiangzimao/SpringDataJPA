package org.jqiaofu.spring.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jqiaofu.spring.domain.User;
import org.jqiaofu.spring.job.testProcessor;
import org.jqiaofu.spring.repository.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {
	
	@Inject
    private JobBuilderFactory jobBuilderFactory;
	
	@Inject
    private StepBuilderFactory stepBuilderFactory;
	
	@Inject
    private PlatformTransactionManager transactionManager;
	
	@Inject
	private UserRepository userRepository;
	
	@Bean
	public Job testJob(){
		return jobBuilderFactory.get("testJob").start(testStep()).build();
	}

	@Bean
	public Step testStep() {
		return stepBuilderFactory.get("testStep")
				.allowStartIfComplete(true)
				.transactionManager(transactionManager)
				.<User,User>chunk(100)
				.reader(testReader())
				.processor(testProcessor())
				.writer(testWriter())
				.build();
	}

	@Bean
	@StepScope
	public ItemReader<User> testReader() {
		RepositoryItemReader<User> reader = new RepositoryItemReader<User>();
		reader.setPageSize(1);
		reader.setMethodName("findUserByAge");
		reader.setRepository(userRepository);
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put("id", Sort.Direction.ASC);
		reader.setSort(sorts);
		return reader;
	}
	
	@Bean
	public ItemProcessor<User, User> testProcessor(){
		return new testProcessor();
	}
	
	@Bean
	@StepScope
	public ItemWriter<User> testWriter() {
		RepositoryItemWriter<User> writer = new RepositoryItemWriter<User>();
		writer.setRepository(userRepository);
		writer.setMethodName("saveAndFlush");
		return writer;
	}

}
