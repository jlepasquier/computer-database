package com.excilys.computerdatabase.persistence;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.excilys.computerdatabase" })
@ComponentScan({ "com.excilys.computerdatabase.dao", "com.excilys.computerdatabase.persistence" })
public class PersistenceContext {

    private static HikariConfig config = new HikariConfig("/datasource.properties");

    @Bean(destroyMethod = "close")
    DataSource dataSource() {
        return new HikariDataSource(config);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.excilys.computerdatabase.model");

        Properties jpaProperties = new Properties();
        ResourceBundle bundle = ResourceBundle.getBundle("hibernate");

        jpaProperties.put("hibernate.dialect", bundle.getString("hibernate.dialect"));
        jpaProperties.put("hibernate.hbm2ddl.auto", bundle.getString("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show_sql", bundle.getString("hibernate.show_sql"));
        //jpaProperties.put("hibernate.ejb.naming_strategy", bundle.getString("hibernate.ejb.naming_strategy"));
        //jpaProperties.put("hibernate.format_sql", bundle.getString("hibernate.format_sql"));        
        
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
    

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
