package org.jqiaofu.spring.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
//加载资源文件
@PropertySource({"classpath:/config/db.properties"})
public class DataSourceConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Inject
    private Environment env;
    
    @Bean(name = "dataSource")  
    public DataSource dataSource() {  
        logger.info("dataSource init ...");  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();  
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));  
        dataSource.setUrl(env.getProperty("jdbc.url"));  
        dataSource.setUsername(env.getProperty("jdbc.username"));  
        dataSource.setPassword(env.getProperty("jdbc.password"));  
        return dataSource;  
    } 
    
    /*
    @Bean
	public JndiObjectFactoryBean jndiObjectFactoryBean(){
		JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
	//	factory.setJndiName("java:comp/env/jdbc/demoDB"); //两种方式均可，spring会自动补齐
        factory.setJndiName("jdbc/demoDB");
		return factory;
	}
	
	@Bean(name = "dataSource")
	public DataSource dataSource() throws Exception{
		logger.info("DataSourceJNDI");
		return (DataSource)jndiObjectFactoryBean().getObject();
		
	}*/

}
