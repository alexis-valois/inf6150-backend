package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.EBEnum;
import com.ezbudget.exception.OperationDisallowedException;
import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.rowmapper.EBEnumRowMapper;
import com.ezbudget.utils.SqlUtils;

@Repository
public class EnumRepository implements IRepository<EBEnum> {

	private static final String TABLE_NAME = "enums";
	private String SINGULAR_NAME = "enum";
	private String PLURAL_NAME = "enums";
	private boolean DELETABLE = false;

	private SimpleJdbcInsert insertTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	@PostConstruct
	private void registerRepository() {
		repositories.put(TABLE_NAME, this);
		this.insertTemplate = new SimpleJdbcInsert(this.jdbcTemplate);
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("id");
	}

	@Override
	public String getSingularName() {
		return this.SINGULAR_NAME;
	}

	@Override
	public String getPluralName() {
		return PLURAL_NAME;
	}

	@Override
	public List<EBEnum> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		List<Filter> filters = criteria.getFilters();
		if ((filters == null) || (filters.size() == 0)) {
			return this.findAll(sessionToken);
		}
		String sql = SqlUtils.getFetchByQueryCriteriaSqlQuery(TABLE_NAME, criteria, DELETABLE);
		return this.jdbcTemplate.query(sql, new Object[0], new EBEnumRowMapper());
	}

	@Override
	public List<EBEnum> findAll(String sessionToken) throws Exception {
		String sqlQuery = "SELECT * FROM enums";
		return jdbcTemplate.query(sqlQuery, new EBEnumRowMapper());
	}

	@Override
	public long count(QueryCriteria criteria, String sessionToken) {
		String countSql = SqlUtils.getCountSqlQuery(TABLE_NAME, criteria, DELETABLE);
		return this.jdbcTemplate.queryForObject(countSql, Long.class).longValue();
	}

	@Override
	public EBEnum findOne(long id, String sessionToken) throws Exception {
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("id", "eq", Long.toString(id)));
		List<EBEnum> enumsList = findByCriteria(qc, sessionToken);
		return enumsList.get(0);
	}

	@Override
	public void update(EBEnum updated, String sessionToken) throws Exception {
		throw new OperationDisallowedException(HttpMethod.PUT, new Exception(""));
	}

	@Override
	public long create(EBEnum newInstance, String sessionToken) throws Exception {
		throw new OperationDisallowedException(HttpMethod.POST, new Exception(""));
	}

	@Override
	public void delete(int entityId, String sessionToken) throws Exception {
		throw new OperationDisallowedException(HttpMethod.DELETE, new Exception(""));

	}

	@Override
	public void validateEntity(EBEnum entity) throws Exception {
		// TODO Auto-generated method stub

	}

}
