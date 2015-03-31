package org.jqiaofu.spring.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
//enable jpa repositories
@EnableJpaRepositories(basePackages = {"org.jqiaofu.spring"})
//@EnableJpaAuditing(auditorAwareRef = "auditor")
public class JpaConfig implements TransactionManagementConfigurer {

	@Inject
	private DataSource dataSource;
	
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPersistenceUnitName("jpa");
		emf.setPersistenceXmlLocation("META-INF/persistence.xml");
		emf.setPersistenceProvider(new HibernatePersistenceProvider());
		return emf;
	}
	
	@Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
