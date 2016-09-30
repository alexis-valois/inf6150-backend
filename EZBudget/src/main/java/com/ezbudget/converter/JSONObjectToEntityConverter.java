package com.ezbudget.converter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.ezbudget.entity.IEntity;
import com.google.common.base.CaseFormat;
import com.google.gson.Gson;

@Service
public class JSONObjectToEntityConverter implements Converter<JSONObject, IEntity> {

	private static final Logger logger = LoggerFactory.getLogger(JSONObjectToEntityConverter.class);

	@Autowired
	private Gson gson;

	@Override
	public IEntity convert(JSONObject source) {
		String type = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, source.getString("entityType"));
		// type = "EB" + type;
		try {
			Class<?> clazz = Class.forName("com.ezbudget.entity." + type);
			return (IEntity) gson.fromJson(source.toString(), clazz);
		} catch (Throwable e) {
			type = "EB" + type;
			try {
				Class<?> clazz = Class.forName("com.ezbudget.entity." + type);
				return (IEntity) gson.fromJson(source.toString(), clazz);
			} catch (Throwable e2) {
				logger.debug(e.getMessage());
			}
		}
		return null;
	}
}