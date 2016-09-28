package com.ezbudget.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezbudget.annotation.Access;
import com.ezbudget.converter.JSONObjectToEntityConverter;
import com.ezbudget.entity.IEntity;
import com.ezbudget.enumtype.RoleType;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.repository.IRepository;
import com.ezbudget.service.QueryService;
import com.ezbudget.utils.HttpUtils;
import com.ezbudget.web.RestRessourceAssembler;

@Access(role = RoleType.USER)
@RestController
@RequestMapping({ "/rest/entity" })
public class EntityController {
	private static Logger logger = LoggerFactory.getLogger(EntityController.class);

	@Autowired
	private RestRessourceAssembler assembler;

	@Autowired
	private HttpUtils httpUtils;

	@Autowired
	private JSONObjectToEntityConverter jsonObjectToEntityConverter;

	@Autowired
	private HashMap<String, IRepository<IEntity>> repositories;

	@Autowired
	private QueryService queryService;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.GET }, value = { "/{entityName}" })
	@ResponseBody
	JSONArray findAll(@PathVariable("entityName") String entityName,
			@RequestHeader(value = "sessionToken") String sessionToken, HttpServletRequest request) {
		QueryCriteria criteria = httpUtils.getQueryCriteria(request);
		JSONArray rtn = new JSONArray();
		if (repositories.containsKey(entityName)) {
			List<IEntity> entities;
			try {
				entities = queryService.findByQueryCriteria(entityName, criteria, sessionToken);
				rtn = this.assembler.getJSONResource((List<Object>) (Object) entities);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}
		return rtn;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/{entityName}/{id}" })
	@ResponseBody
	JSONObject findOne(@PathVariable("entityName") String entityName,
			@RequestHeader(value = "sessionToken") String sessionToken, @PathVariable("id") int entityId) {
		JSONObject rtn = new JSONObject();
		try {
			IEntity entity = queryService.getById(entityName, entityId, sessionToken);
			rtn = this.assembler.getJSONResource(entity);
		} catch (Exception e) {
			rtn.put("error", "entity not found");
		}
		return rtn;
	}

	@RequestMapping(method = { RequestMethod.PUT }, value = { "/{entityName}/{id}" }, headers = {
			"content-type!=multipart/form-data" })
	@ResponseBody
	JSONObject update(@RequestBody JSONObject json, @RequestHeader(value = "sessionToken") String sessionToken,
			@PathVariable("entityName") String entityName, @PathVariable("id") int id) {
		IEntity entity = null;
		try {
			if (repositories.containsKey(entityName)) {
				json.put("entityType", repositories.get(entityName).getSingularName());
				json.put("id", id);
				entity = jsonObjectToEntityConverter.convert(json);
				repositories.get(entityName).update(entity, sessionToken);
			}
			json.put("updateStatus", "updated");
		} catch (Throwable e) {
			logger.error(e.getMessage());
			json.put("updateStatus", e.getMessage());
		}

		return json;
	}

	@RequestMapping(method = { RequestMethod.POST }, value = { "/{entityName}" }, headers = {
			"content-type!=multipart/form-data" })
	@ResponseBody
	JSONObject create(@RequestBody JSONObject json, @RequestHeader(value = "sessionToken") String sessionToken,
			@PathVariable("entityName") String entityName) {

		IEntity entity = null;
		try {
			if (repositories.containsKey(entityName)) {
				json.put("entityType", repositories.get(entityName).getSingularName());
				entity = jsonObjectToEntityConverter.convert(json);
				repositories.get(entityName).create(entity, sessionToken);
			}
			json.put("createStatus", "created");
		} catch (Throwable e) {
			logger.error(e.getMessage());
			json.put("createStatus", e.getMessage());
		}

		return json;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/{entityName}/count" })
	@ResponseBody
	JSONObject count(@PathVariable("entityName") String entityName,
			@RequestHeader(value = "sessionToken") String sessionToken, HttpServletRequest request) {
		QueryCriteria criteria = httpUtils.getQueryCriteria(request);
		JSONObject rtn = new JSONObject();
		if (repositories.containsKey(entityName)) {
			long count;
			try {
				count = queryService.count(entityName, criteria, sessionToken);
				rtn.put("count", count);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}
		return rtn;
	}

	@RequestMapping(method = { RequestMethod.DELETE }, value = { "/{entityName}/{id}" })
	@ResponseBody
	JSONObject delete(@PathVariable("entityName") String entityName,
			@RequestHeader(value = "sessionToken") String sessionToken, @PathVariable("id") int entityId) {
		JSONObject json = new JSONObject();

		try {
			if (repositories.containsKey(entityName)) {
				json.put("entityName", repositories.get(entityName).getSingularName());
				json.put("entityId", entityId);
				repositories.get(entityName).delete(entityId, sessionToken);
				json.put("deleteStatus", "deleted");
			}
		} catch (Throwable e) {
			logger.error(e.getMessage());
			json.put("deleteStatus", e.getMessage());
		}

		return json;
	}
}
