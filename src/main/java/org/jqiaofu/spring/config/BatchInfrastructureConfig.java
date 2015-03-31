package org.jqiaofu.spring.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchInfrastructureConfig implements BatchConfigurer {

	@Inject
	DataSource dataSource;

	@Inject
	PlatformTransactionManager transactionManager;

	@Inject
	private EntityManagerFactory entityManagerFactory;

	private JobRepository jobRepository;
	
	private JobLauncher jobLauncher;

	@Inject
	private JobRegistry jobRegistry;

	@Inject
	private ResourceLoader resourceLoader;
	
	@Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

	@PostConstruct
    public void initialize() throws Exception {
        this.initialzeBatchTables();
        this.jobRepository = createJobRepository();
        this.jobLauncher = createJobLauncher();
    }
	
	
	private void initialzeBatchTables() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-drop-mysql.sql"));
        populator.addScript(resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-mysql.sql"));
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator, dataSource);
	}


	protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(this.dataSource);
        factory.setTransactionManager(this.transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
        factory.afterPropertiesSet();
        return (JobRepository) factory.getObject();
    }
	
	private JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

	public JobRepository getJobRepository() throws Exception {
		return jobRepository;
	}

	public PlatformTransactionManager getTransactionManager() throws Exception {
		return this.transactionManager;
	}

	public JobLauncher getJobLauncher() throws Exception {
		return this.jobLauncher;
	}

	public JobExplorer getJobExplorer() throws Exception {
		JobExplorerFactoryBean bean = new JobExplorerFactoryBean();
        bean.setDataSource(dataSource);
        bean.afterPropertiesSet();
        return (JobExplorer) bean.getObject();
	}

}