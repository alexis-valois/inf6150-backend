package com.ezbudget.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ezbudget.serializer.DateTimeTypeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class DefaultConfig {

	@Bean
	public Gson createDefaultGson() {
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(DateTime.class, new DateTimeTypeSerializer());
		return gson.create();
	}

	@Bean
	public Validator getDefaultValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate rest = new RestTemplate();
		return rest;
	}
}
