package com.ezbudget.repository;

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

import com.ezbudget.entity.Bill;
import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.rowmapper.BillsRowMapper;
import com.ezbudget.service.AuthenticationService;
import com.ezbudget.utils.SqlUtils;

@Repository
public class BillRepository implements IRepository<Bill> {
	
	private static final String TABLE_NAME = "bills";
	private String SINGULAR_NAME = "bill";
	private String PLURAL_NAME = "bills";
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
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("bill_id");
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
		countSql = SqlUtils.insertJointToSql(countSql, "INNER JOIN users ON bills.userId = users.user_id");
		return this.jdbcTemplate.queryForObject(countSql, Long.class).longValue();
	}
	
	@Override
	public List<Bill> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		List<Filter> filters = criteria.getFilters();
		if ((filters == null) || (filters.size() == 0)) {
			return this.findAll(sessionToken);
		}
		String sql = getSessionTokenDrivenResultSetRestriction(criteria, sessionToken, filters);
		return this.jdbcTemplate.query(sql, new Object[0], new BillsRowMapper());
	}
	
	private String getSessionTokenDrivenResultSetRestriction(QueryCriteria criteria, String sessionToken,
			List<Filter> filters) {
		filters.add(new Filter("session_token", "eq", sessionToken));
		String sql = SqlUtils.getFetchByQueryCriteriaSqlQuery(TABLE_NAME, criteria, DELETABLE);
		sql = SqlUtils.insertJointToSql(sql, "INNER JOIN users ON bills.userId = users.user_id");
		return sql;
	}
	@Override
	public List<Bill> findAll(String sessionToken) throws Exception {
		String sqlQuery = "SELECT * FROM bills ac INNER JOIN users u ON ac.userId = u.user_id HAVING u.session_token = ? AND ac.deleted != 1";
		return jdbcTemplate.query(sqlQuery, new BillsRowMapper(), new Object[] { sessionToken });
	}
	@Override
	public Bill findOne(long id, String sessionToken) throws Exception {
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("id", "eq", Long.toString(id)));
		List<Bill> billList = findByCriteria(qc, sessionToken);
		return billList.get(0);
	}
	
	@Override
	public synchronized void update(Bill updated, String sessionToken) throws Exception {
		Money amount = updated.getAmount();
		CurrencyUnit currency = amount.getCurrencyUnit();
		long categoriesId = updated.getCategorieId();
		long suppliersId = updated.getSupplierId();
		long accountId = updated.getAccountId();
		long billId = updated.getId();
		
		String sql = "UPDATE " + TABLE_NAME + " SET amount = ? , " + " currency = ?, " + "categorieId = ?, " + "supplierId = ?, accountId = ? "
				+ "WHERE id = ? AND deleted != 1 AND userId = (SELECT user_id FROM users WHERE session_token = ?)";
		int updatedRows = this.jdbcTemplate.update(sql, new Object[] { 
				amount.getAmount(),
				currency.getCurrencyCode(),
				categoriesId,
				suppliersId,
				accountId,
				billId,
				sessionToken }
		);

		if (updatedRows < 1) {
			throw new RuntimeException("Unable to update id = " + accountId);
		}

	}
	
	@Override
	public synchronized long create(Bill newInstance, String sessionToken) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deleted", false);
		param.put("userId", authService.getAuthenticatedUserInfo(sessionToken).getId());
		param.put("amount", newInstance.getAmount().getAmount());
		//param.put("created", newInstance.getCreated());
		param.put("currency", newInstance.getAmount().getCurrencyUnit().getCurrencyCode());
		param.put("categorieId", newInstance.getCategorieId());
		param.put("supplierId", newInstance.getSupplierId());
		param.put("accountId", newInstance.getAccountId());
		Number generatedId = this.insertTemplate.executeAndReturnKey(param);
		if (generatedId.longValue() < 1) {
			throw new RuntimeException("Unable to create new Bill");
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
	public void validateEntity(Bill entity) throws Exception {
		// TODO Auto-generated method stub

	}
	
}



























