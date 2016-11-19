package com.ezbudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class })
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class EzBudgetApplication {

	public static void main(String[] args) {
		args = new String[]{"--spring.profiles.active=serge"};
		SpringApplication.run(EzBudgetApplication.class, args);
	}
}
