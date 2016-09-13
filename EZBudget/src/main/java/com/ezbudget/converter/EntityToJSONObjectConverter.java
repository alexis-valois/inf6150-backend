package com.ezbudget.converter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class EntityToJSONObjectConverter implements
		Converter<Object, JSONObject> {
	private static final Logger logger = LoggerFactory
			.getLogger(EntityToJSONObjectConverter.class);

	@Autowired
	private Gson gson;

	@Override
	public JSONObject convert(Object source) {
		JSONObject rtn = new JSONObject();
		rtn = new JSONObject(gson.toJson(source));
		return rtn;
	}

}
