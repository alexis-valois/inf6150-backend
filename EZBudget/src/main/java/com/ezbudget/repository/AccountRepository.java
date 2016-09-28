package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.Account;
import com.ezbudget.filter.QueryCriteria;

@Repository
public class AccountRepository implements IRepository<Account> {

	private static final String TABLE_NAME = "accounts";
	private String SINGULAR_NAME = "account";
	private String PLURAL_NAME = "accounts";

	private SimpleJdbcInsert insertTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	@PostConstruct
	private void registerRepository() {
		repositories.put(TABLE_NAME, this);
		this.insertTemplate = new SimpleJdbcInsert(this.jdbcTemplate);
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("account_id");
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Account> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAll(String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account findOne(long id, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Account updated, String sessionToken) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long create(Account newInstance, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int entityId, String sessionToken) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateEntity(Account entity) throws Exception {
		// TODO Auto-generated method stub

	}

}
