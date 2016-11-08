package com.ezbudget.repository;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.Revenue;

import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;

import com.ezbudget.rowmapper.RevenuesRowMapper;
import com.ezbudget.service.AuthenticationService;
import com.ezbudget.utils.SqlUtils;

@Repository
public class RevenuesRepository implements IRepository<Revenue> {
	
	
	
	
	private static final String TABLE_NAME = "revenues";
	private String SINGULAR_NAME = "revenue";
	private String PLURAL_NAME = "revenues";
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
	public long count(QueryCriteria criteria, String sessionToken) {
		List<Filter> filters = criteria.getFilters();
		filters.add(new Filter("session_token", "eq", sessionToken));
		String countSql = SqlUtils.getCountSqlQuery(TABLE_NAME, criteria, DELETABLE);
		countSql = SqlUtils.insertJointToSql(countSql, "INNER JOIN users ON revenues.userId = users.user_id");
		return this.jdbcTemplate.queryForObject(countSql, Long.class).longValue();
	}

	@Override
	public List<Revenue> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		List<Filter> filters = criteria.getFilters();
		if ((filters == null) || (filters.size() == 0)) {
			return this.findAll(sessionToken);
		}
		String sql = getSessionTokenDrivenResultSetRestriction(criteria, sessionToken, filters);
		return this.jdbcTemplate.query(sql, new Object[0], new RevenuesRowMapper());
	}

	private String getSessionTokenDrivenResultSetRestriction(QueryCriteria criteria, String sessionToken,
			List<Filter> filters) {
		filters.add(new Filter("session_token", "eq", sessionToken));
		String sql = SqlUtils.getFetchByQueryCriteriaSqlQuery(TABLE_NAME, criteria, DELETABLE);
		sql = SqlUtils.insertJointToSql(sql, "INNER JOIN users ON revevues.userId = users.user_id");
		return sql;
	}

	@Override
	public List<Revenue> findAll(String sessionToken) throws Exception {
		String sqlQuery = "SELECT * FROM revenues rv INNER JOIN users u ON rv.userId = u.user_id HAVING u.session_token = ? AND rv.deleted != 1";
		return jdbcTemplate.query(sqlQuery, new RevenuesRowMapper(), new Object[] { sessionToken });
	}

	@Override
	public Revenue findOne(long id, String sessionToken) throws Exception {
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("id", "eq", Long.toString(id)));
		List<Revenue> revenueList = findByCriteria(qc, sessionToken);
		return revenueList.get(0);
	}

	@Override
	public synchronized void update(Revenue updated, String sessionToken) throws Exception {
		
		long id = updated.getId();		
		Money amount = updated.getAmount();
		String frequency=updated.getFrequency();
		DateTime rev_starting = updated.getRevStarting();
		DateTime rev_ending = updated.getRevEnding();
		CurrencyUnit currency = amount.getCurrencyUnit();
		
		String sql = "UPDATE " + TABLE_NAME + " SET amount = ?, " + "frequency = ?,"+ "rev_starting = ?,"+ "rev_ending = ?," + "currency = ?"
				+ "WHERE id = ? AND deleted != 1 AND userId = (" + "SELECT user_id FROM users WHERE session_token = ?"
				+ ")";
		int updatedRows = this.jdbcTemplate.update(sql, new Object[] {  amount.getAmount(),
				currency.getCurrencyCode(), id, sessionToken });

		if (updatedRows < 1) {
			throw new RuntimeException("Unable to update id = " + id);
		}

	}

	@Override
	public synchronized long create(Revenue newInstance, String sessionToken) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("frequency", newInstance.getFrequency().toString());	
		param.put("amount", newInstance.getAmount().getAmount()); 
		param.put("currency", newInstance.getAmount().getCurrencyUnit().getCurrencyCode());
		param.put("userId", authService.getAuthenticatedUserInfo(sessionToken).getId()); 
		param.put("accountId", newInstance.getAccountId());
		param.put("deleted", false);
		param.put("rev_starting", new Timestamp(newInstance.getRevStarting().getMillis()));
		param.put("rev_ending", new Timestamp(newInstance.getRevEnding().getMillis()));
		
		Number generatedId = this.insertTemplate.executeAndReturnKey(param);
		if (generatedId.longValue() < 1) {
			throw new RuntimeException("Unable to create new Revenu");
		}
		return generatedId.longValue();
	}

	@Override
	public void delete(int revenueId, String sessionToken) throws Exception {
		if (DELETABLE) {
			String sql = "UPDATE " + TABLE_NAME + " SET deleted = 1 " + "WHERE id = ? AND userId = ("
					+ "SELECT user_id FROM users WHERE session_token = ?" + ")";
			int updatedRows = this.jdbcTemplate.update(sql, new Object[] { revenueId, sessionToken });
			if (updatedRows < 1) {
				throw new RuntimeException("Unable to delete id = " + revenueId);
			}
		}
	}

	@Override
	public void validateEntity(Revenue revenue) throws Exception {
		// TODO Auto-generated method stub

	}


}