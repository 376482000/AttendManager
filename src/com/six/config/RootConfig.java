package com.six.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={"com.six.dao","com.six.service"})
@EnableWebMvc
public class RootConfig {
	@Bean
	public BasicDataSource dataSource(){
		
		BasicDataSource bds=new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://10.20.132.2:3306/attend?useUnicode=true&characterEncoding=utf8");
		bds.setUsername("root");
		bds.setPassword("112233");
		bds.setMaxActive(1000);
		return bds;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(BasicDataSource dataSource){
		LocalSessionFactoryBean sfb=new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan(new String[] {"com.six.model"});  
		Properties props=new Properties();
		props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		props.setProperty("current_session_context_class", "thread");
		sfb.setHibernateProperties(props);
		
		return sfb;
	}
	
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();     
        transactionManager.setSessionFactory(sessionFactory);       
        return transactionManager;
    }
}
