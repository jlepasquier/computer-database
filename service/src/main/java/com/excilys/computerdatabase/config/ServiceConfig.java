package com.excilys.computerdatabase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.computerdatabase.persistence.PersistenceContext;

@Configuration
@Import(PersistenceContext.class)
@ComponentScan({"com.excilys.computerdatabase.service"})
public class ServiceConfig {
}
