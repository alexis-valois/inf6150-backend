package com.ezbudget.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ezbudget.serializer.DateTimeTypeSerializer;
import com.ezbudget.serializer.MoneyTypeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class DefaultConfig {

	@Bean
	public Gson createDefaultGson() {
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(DateTime.class, new DateTimeTypeSerializer());
		gson.registerTypeAdapter(Money.class, new MoneyTypeSerializer());
		return gson.create();
	}

	@Bean
	public Validator getDefaultValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator;
	}
}
