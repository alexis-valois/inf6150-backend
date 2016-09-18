package com.ezbudget.web;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezbudget.converter.EntityToJSONObjectConverter;

@Component
public class RestRessourceAssembler {

	@Autowired
	private EntityToJSONObjectConverter convertor;

	public JSONArray getJSONResource(List<Object> entities) throws JSONException {
		JSONArray rtn = new JSONArray();
		for (Object entity : entities) {
			rtn.put(getJSONResource(entity));
		}
		return rtn;
	}

	public JSONObject getJSONResource(Object entity) throws JSONException {
		return this.convertor.convert(entity);
	}
}
