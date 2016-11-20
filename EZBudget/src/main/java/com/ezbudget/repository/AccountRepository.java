package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.Account;
import com.ezbudget.entity.Bill;
import com.ezbudget.entity.Revenue;
import com.ezbudget.enumtype.AccountType;
import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.rowmapper.AccountRowMapper;
import com.ezbudget.service.AuthenticationService;
import com.ezbudget.utils.SqlUtils;

@Repository
public class AccountRepository implements IRepository<Account> {

	private static final String TABLE_NAME = "accounts";
	private String SINGULAR_NAME = "account";
	private String PLURAL_NAME = "accounts";
	private boolean DELETABLE = true;

	private SimpleJdbcInsert insertTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private HashMap<String, IRepository<?>> repositories;
	
	@Autowired
	private RevenuesRepository revenuRepo;
	
	@Autowired
	private BillRepository billRepo;

	@PostConstruct
	private void registerRepository() {
		repositories.put(TABLE_NAME, this);
		this.insertTemplate = new SimpleJdbcInsert(this.jdbcTemplate);
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("account_id");
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
		countSql = SqlUtils.insertJointToSql(countSql, "INNER JOIN users ON accounts.userId = users.user_id");
		return this.jdbcTemplate.queryForObject(countSql, Long.class).longValue();
	}

	@Override
	public List<Account> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		List<Filter> filters = criteria.getFilters();
		if ((filters == null) || (filters.size() == 0)) {
			return this.findAll(sessionToken);
		}
		String sql = getSessionTokenDrivenResultSetRestriction(criteria, sessionToken, filters);
		return this.jdbcTemplate.query(sql, new Object[0], new AccountRowMapper());
	}

	private String getSessionTokenDrivenResultSetRestriction(QueryCriteria criteria, String sessionToken,
			List<Filter> filters) {
		filters.add(new Filter("session_token", "eq", sessionToken));
		String sql = SqlUtils.getFetchByQueryCriteriaSqlQuery(TABLE_NAME, criteria, DELETABLE);
		sql = SqlUtils.insertJointToSql(sql, "INNER JOIN users ON accounts.userId = users.user_id");
		return sql;
	}

	@Override
	public List<Account> findAll(String sessionToken) throws Exception {
		String sqlQuery = "SELECT * FROM accounts ac INNER JOIN users u ON ac.userId = u.user_id HAVING u.session_token = ? AND ac.deleted != 1";
		return jdbcTemplate.query(sqlQuery, new AccountRowMapper(), new Object[] { sessionToken });
	}

	@Override
	public Account findOne(long id, String sessionToken) throws Exception {
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("id", "eq", Long.toString(id)));
		List<Account> accountList = findByCriteria(qc, sessionToken);
		return accountList.get(0);
	}

	@Override
	public synchronized void update(Account updated, String sessionToken) throws Exception {
		long id = updated.getId();
		AccountType type = updated.getType();
		String name = updated.getAccountName();
		Money initAmount = updated.getInitAmount();
		CurrencyUnit currency = initAmount.getCurrencyUnit();
		String sql = "UPDATE " + TABLE_NAME + " SET type = ? , " + " name = ?, " + "initAmount = ?, " + "currency = ?"
				+ "WHERE id = ? AND deleted != 1 AND userId = (" + "SELECT user_id FROM users WHERE session_token = ?"
				+ ")";
		int updatedRows = this.jdbcTemplate.update(sql, new Object[] { type.toString(), name, initAmount.getAmount(),
				currency.getCurrencyCode(), id, sessionToken });

		if (updatedRows < 1) {
			throw new RuntimeException("Unable to update id = " + id);
		}

	}

	@Override
	public synchronized long create(Account newInstance, String sessionToken) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", newInstance.getType().toString());
		param.put("name", newInstance.getAccountName());
		param.put("initAmount", newInstance.getInitAmount().getAmount());
		param.put("currency", newInstance.getInitAmount().getCurrencyUnit().getCurrencyCode());
		param.put("userId", authService.getAuthenticatedUserInfo(sessionToken).getId());
		param.put("deleted", false);
		Number generatedId = this.insertTemplate.executeAndReturnKey(param);
		if (generatedId.longValue() < 1) {
			throw new RuntimeException("Unable to create new Account");
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
	public void validateEntity(Account entity) throws Exception {
		// TODO Auto-generated method stub

	}

	
	public Money getSolde(String sessionToken, int accountId, DateTime queryDate) throws Exception {
		
		Account account = this.findOne(accountId,sessionToken);
		QueryCriteria qc = new QueryCriteria();
		List<Filter> filters = qc.getFilters();
		filters.add(new Filter("accountId", "eq", Long.toString(accountId)));
		List<Revenue> revenuList = revenuRepo.findByCriteria(qc, sessionToken);
		
		Money initAmount = account.getInitAmount();
		Money solde = initAmount;
		
		for (Revenue revenu : revenuList) {
			
			if (queryDate.isAfter(revenu.getRevStarting())  && queryDate.isBefore(revenu.getRevStarting())){
				
				switch(revenu.getFrequency()){
				   
			    	case "WEEKLY":
			    		solde = solde.plus(extraireSole(queryDate, revenu, 7));
			    		break;
			    	case "ONCE":
			    		solde = solde.plus(revenu.getAmount());
			    		break;
			    	case "BI-WEEKLY":
			    		solde = solde.plus(extraireSole(queryDate, revenu, 14));
			    		break;
			    	case "MONTHLY":
			    		solde = solde.plus(extraireSole(queryDate, revenu, 30));
				}
				
			} 
		}
		List<Bill> listBill = billRepo.findAll(sessionToken);
		for (Bill bill : listBill) {
			solde = solde.minus(bill.getAmount());
		}
		return solde;
	}
	
	private Money extraireSole(DateTime queryDate, Revenue revenu, int code) {
		Days d = Days.daysBetween(revenu.getCreated(), queryDate);    
		int nombreJour = d.getDays();
		int diviseur = nombreJour/code;
		return  revenu.getAmount().multipliedBy(diviseur);
	}

}
