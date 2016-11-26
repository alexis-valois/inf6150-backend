package com.ezbudget.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezbudget.entity.IEntity;
import com.ezbudget.filter.SubEntity;
import com.ezbudget.repository.IRepository;
import com.ezbudget.web.RestRessourceAssembler;

@Service
public class SubEntityService {

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	@Autowired
	private RestRessourceAssembler assembler;

	public void insertSubEntities(JSONArray entities, List<SubEntity> subEntitiesRequirements, String sessionToken)
			throws JSONException, Exception {
		for (int i = 0; i < entities.length(); i++) {
			JSONObject entity = entities.getJSONObject(i);
			for (SubEntity sub : subEntitiesRequirements) {
				if (repositories.containsKey(sub.getEntityName())) {
					IRepository<?> rep = repositories.get(sub.getEntityName());
					IEntity subEntity = (IEntity) rep.findOne(entity.getLong(sub.getForeignKeyName()), sessionToken);
					JSONObject jsonSubEntity = assembler.getJSONResource(subEntity);
					entity.append(sub.getEntityName(), jsonSubEntity);

				}
			}
		}
	}
}
