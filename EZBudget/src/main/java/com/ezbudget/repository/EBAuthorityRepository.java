package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.EBAuthority;
import com.ezbudget.filter.QueryCriteria;

@Repository
public class EBAuthorityRepository implements IRepository<EBAuthority> {

	private static final String TABLE_NAME = "authorities";
	private String SINGULAR_NAME = "authority";
	private String PLURAL_NAME = "authorities";

	private SimpleJdbcInsert insertTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	@Autowired
	private Validator validator;

	@PostConstruct
	private void registerRepository() {
		repositories.put(TABLE_NAME, this);
		this.insertTemplate = new SimpleJdbcInsert(this.jdbcTemplate);
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("authorities_id");
		;
	}

	public synchronized void register(EBAuthority authority) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("authority", authority.getAuthority());
		param.put("fk_username", authority.getUsername());

		int updatedRows = this.insertTemplate.execute(param);
		if (updatedRows < 1) {
			throw new RuntimeException("Unable to create new authority");
		}
	}

	@Override
	public void delete(int entityId, String sessionToken) throws Exception {
		// TODO Auto-generated method stub

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
	public List<EBAuthority> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EBAuthority> findAll(String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EBAuthority findOne(long id, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(EBAuthority updated, String sessionToken) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long create(EBAuthority newInstance, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validateEntity(EBAuthority entity) throws Exception {
		// TODO Auto-generated method stub

	}

}
