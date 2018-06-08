package com.excilys.computerdatabase.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@Profile("web")
@Import(ServiceConfig.class)
@ComponentScan(basePackages = { "com.excilys.computerdatabase.controller", "com.excilys.computerdatabase.config" })
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("WEB-INF/views/pages/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");

        // registry.addResourceHandler("/WEB-INF/js/**").addResourceLocations("/WEB-INF/js/");
        // registry.addResourceHandler("/WEB-INF/css/**").addResourceLocations("/WEB-INF/css/");
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
