package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.Supplier;
import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.rowmapper.SupplierRowMapper;
import com.ezbudget.service.AuthenticationService;
import com.ezbudget.utils.SqlUtils;

@Repository
public class SupplierRepository implements IRepository<Supplier> {
	
	private static final String TABLE_NAME = "suppliers";
	private String SINGULAR_NAME = "supplier";
	private String PLURAL_NAME = "suppliers";
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
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("supplier_id");
	}
	
	private String getSessionTokenDrivenResultSetRestriction(QueryCriteria criteria, String sessionToken,
			List<Filter> filters) {
		filters.add(new Filter("session_token", "eq", sessionToken));
		String sql = SqlUtils.getFetchByQueryCriteriaSqlQuery(TABLE_NAME, criteria, DELETABLE);
		sql = SqlUtils.insertJointToSql(sql, "INNER JOIN users ON suppliers.userId = users.user_id");
		return sql;
	}
	
	@Override
	public List<Supplier> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		List<Filter> filters = criteria.getFilters();
		if ((filters == null) || (filters.size() == 0)) {
			return this.findAll(sessionToken);
		}
		String sql = getSessionTokenDrivenResultSetRestriction(criteria, sessionToken, filters);
		return this.jdbcTemplate.query(sql, new Object[0], new SupplierRowMapper());
	}

	@Override
	public List<Supplier> findAll(String sessionToken) throws Exception {
		String sqlQuery = "SELECT * FROM suppliers su INNER JOIN users u ON su.userId = u.user_id HAVING u.session_token = ?";
		return jdbcTemplate.query(sqlQuery, new SupplierRowMapper(), new Object[] { sessionToken });
	}

	@Override
	public long count(QueryCriteria criteria, String sessionToken) {
		List<Filter> filters = criteria.getFilters();
		filters.add(new Filter("session_token", "eq", sessionToken));
		String countSql = SqlUtils.getCountSqlQuery(TABLE_NAME, criteria, DELETABLE);
		countSql = SqlUtils.insertJointToSql(countSql, "INNER JOIN users ON suppliers.userId = users.user_id");
		return this.jdbcTemplate.queryForObject(countSql, Long.class).longValue();
	}

	@Override
	public Supplier findOne(long id, String sessionToken) throws Exception {
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("id", "eq", Long.toString(id)));
		List<Supplier> supplierList = findByCriteria(qc, sessionToken);
		return supplierList.get(0);
	}

	@Override
	public synchronized void update(Supplier updated, String sessionToken) throws Exception {
		long id = updated.getId();
		String name = updated.getSupplierName();
		String sql = "UPDATE " + TABLE_NAME + " SET name = ? "
				+ "WHERE id = ? AND deleted != 1 AND userId = (" + "SELECT user_id FROM users WHERE session_token = ?"
				+ ")";
		int updatedRows = this.jdbcTemplate.update(sql, new Object[] { name, id, sessionToken });

		if (updatedRows < 1) {
			throw new RuntimeException("Unable to update id = " + id);
		}
		
	}

	@Override
	public synchronized long create(Supplier newInstance, String sessionToken) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", newInstance.getSupplierName());
		param.put("userId", authService.getAuthenticatedUserInfo(sessionToken).getId());
		param.put("deleted", false);
		Number generatedId = this.insertTemplate.executeAndReturnKey(param);
		if (generatedId.longValue() < 1) {
			throw new RuntimeException("Unable to create new Supplier");
		}
		return generatedId.longValue();
	}

	@Override
	public void delete(int entityId, String sessionToken) throws Exception {
		if (DELETABLE) {
			String sql = "UPDATE " + TABLE_NAME + " SET deleted = 1 " + "WHERE id = ? AND userId = ("
					+ "SELECT user_id FROM users WHERE session_token = ?" + ")";
			int updatedRows = this.jdbcTemplate.update(sql, new Object[] { entityId, sessionToken });
			if (updatedRows < 1) {
				throw new RuntimeException("Unable to delete id = " + entityId);
			}
		}
	}

	@Override
	public String getSingularName() {
		return SINGULAR_NAME;
	}

	@Override
	public String getPluralName() {
		return PLURAL_NAME;
	}

	@Override
	public void validateEntity(Supplier entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
