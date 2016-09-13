package com.ezbudget.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan
@Configuration
@EnableWebMvc
public class WebConfig extends RestConfigurerAdapter {

	@Bean
	public Filter encodingFilter() {
		CharacterEncodingFilter rtn = new CharacterEncodingFilter();
		rtn.setEncoding("UTF-8");
		rtn.setForceEncoding(true);
		return rtn;
	}
}
