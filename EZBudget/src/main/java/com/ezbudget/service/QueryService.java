package com.ezbudget.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezbudget.entity.IEntity;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.repository.IRepository;

@Service
public class QueryService {

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	public IEntity getById(String entityName, int entityId, String sessionToken) throws Exception {
		IEntity rtn = null;
		if (repositories.containsKey(entityName)) {
			rtn = (IEntity) repositories.get(entityName).findOne(entityId, sessionToken);
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	public List<IEntity> findByQueryCriteria(String entityName, QueryCriteria criteria, String sessionToken)
			throws Exception {
		return (List<IEntity>) this.repositories.get(entityName).findByCriteria(criteria, sessionToken);
	}

	public long count(String entityName, QueryCriteria criteria, String sessionToken) {
		return this.repositories.get(entityName).count(criteria, sessionToken);
	}
}
