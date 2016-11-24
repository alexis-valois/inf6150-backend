package com.ezbudget.repository;

import java.util.List;

import com.ezbudget.filter.QueryCriteria;

public interface IRepository<T> {

	public List<T> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception;

	public long count(QueryCriteria criteria, String sessionToken);

	public T findOne(long id, String sessionToken) throws Exception;

	public void update(T updated, String sessionToken) throws Exception;

	public long create(T newInstance, String sessionToken) throws Exception;

	public void delete(int entityId, String sessionToken) throws Exception;

	public String getSingularName();

	public String getPluralName();

	public void validateEntity(T entity) throws Exception;
}
