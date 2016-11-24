package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.Categorie;
import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.rowmapper.CategorieRowMapper;
import com.ezbudget.service.AuthenticationService;
import com.ezbudget.utils.SqlUtils;

@Repository
public class CategorieRepository implements IRepository<Categorie> {

	private static final String TABLE_NAME = "categories";
	private String SINGULAR_NAME = "categorie";
	private String PLURAL_NAME = "categories";
	private boolean DELETABLE = true;

	private SimpleJdbcInsert insertTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AuthenticationService authService;

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	@PostConstruct
	private void registerRepository() {
		repositories.put(TABLE_NAME, this);
		this.insertTemplate = new SimpleJdbcInsert(this.jdbcTemplate);
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("categories_id");
		;
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
	public long count(QueryCriteria criteria, String sessionToken) {
		List<Filter> filters = criteria.getFilters();
		filters.add(new Filter("session_token", "eq", sessionToken));
		String countSql = SqlUtils.getCountSqlQuery(TABLE_NAME, criteria, DELETABLE);
		countSql = SqlUtils.insertJointToSql(countSql, "INNER JOIN users ON categories.userId = users.user_id");
		return this.jdbcTemplate.queryForObject(countSql, Long.class).longValue();
	}

	@Override
	public List<Categorie> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		String sql = getSessionTokenDrivenResultSetRestriction(criteria, sessionToken);
		return this.jdbcTemplate.query(sql, new Object[0], new CategorieRowMapper());
	}

	private String getSessionTokenDrivenResultSetRestriction(QueryCriteria criteria, String sessionToken) {
		List<Filter> filters = criteria.getFilters();
		filters.add(new Filter("session_token", "eq", sessionToken));
		String sql = SqlUtils.getFetchByQueryCriteriaSqlQuery(TABLE_NAME, criteria, DELETABLE);
		sql = SqlUtils.insertJointToSql(sql, "INNER JOIN users ON categories.userId = users.user_id");
		return sql;
	}

	@Override
	public Categorie findOne(long id, String sessionToken) throws Exception {
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("categories_id", "eq", Long.toString(id)));
		List<Categorie> categorieList = findByCriteria(qc, sessionToken);
		return categorieList.get(0);
	}

	@Override
	public synchronized void update(Categorie updated, String sessionToken) throws Exception {
		long id = updated.getId();
		String name = updated.getCategorieName();
		String sql = "UPDATE " + TABLE_NAME + " SET name = ? "
				+ "WHERE categories_id = ? AND deleted != 1 AND userId = ("
				+ "SELECT user_id FROM users WHERE session_token = ?" + ")";
		int updatedRows = this.jdbcTemplate.update(sql, new Object[] { name, id, sessionToken });

		if (updatedRows < 1) {
			throw new RuntimeException("Unable to update id = " + id);
		}

	}

	@Override
	public synchronized long create(Categorie newInstance, String sessionToken) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", newInstance.getCategorieName());
		param.put("userId", authService.getAuthenticatedUserInfo(sessionToken).getId());
		param.put("deleted", false);
		Number generatedId = this.insertTemplate.executeAndReturnKey(param);
		if (generatedId.longValue() < 1) {
			throw new RuntimeException("Unable to create new Categorie");
		}
		return generatedId.longValue();
	}

	@Override
	public void delete(int entityId, String sessionToken) throws Exception {
		if (DELETABLE) {
			String sql = "UPDATE " + TABLE_NAME + " SET deleted = 1 " + "WHERE categories_id = ? AND userId = ("
					+ "SELECT user_id FROM users WHERE session_token = ?" + ")";
			int updatedRows = this.jdbcTemplate.update(sql, new Object[] { entityId, sessionToken });
			if (updatedRows < 1) {
				throw new RuntimeException("Unable to delete id = " + entityId);
			}
		}
	}

	@Override
	public void validateEntity(Categorie entity) throws Exception {
		// TODO Auto-generated method stub

	}

}
