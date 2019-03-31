package com.yuuitanabe.pipres.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
	@Bean
	  public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
	    driverManagerDataSource.setUrl("jdbc:oracle");
	    driverManagerDataSource.setUsername("root");
	    driverManagerDataSource.setPassword("");
	    return driverManagerDataSource;
	  }
	   
	  @Bean
	  public DataSourceTransactionManager transactionManager() {
	    return new DataSourceTransactionManager(dataSource());
	  }
	   
	  @Bean
	  public JdbcTemplate jdbcTemplate() {
	    return new JdbcTemplate(dataSource());
	  }
	}
