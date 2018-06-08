package com.excilys.computerdatabase.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile({"cli", "default"})
@Import(ServiceConfig.class)
@ComponentScan(basePackages = {"com.excilys.computerdatabase.controller", "com.excilys.computerdatabase.ui"})
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig("/datasource.properties");
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
