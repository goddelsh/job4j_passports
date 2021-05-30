package ru.job4j.passports_service;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class PassportsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassportsServiceApplication.class, args);
	}

	@Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase_conf.xml");
        liquibase.setDataSource(ds);
        return liquibase;
    }
}
