package com.ezbudget.repository;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ezbudget.entity.EBUser;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.rowmapper.EBUserResultSetExtractor;

@Repository
public class EBUserRepository implements IRepository<EBUser> {

	private static Logger logger = LoggerFactory.getLogger(EBUserRepository.class);

	private static final String TABLE_NAME = "users";
	private String SINGULAR_NAME = "user";
	private String PLURAL_NAME = "users";

	@PostConstruct
	private void registerRepository() {
		repositories.put(TABLE_NAME, this);
		this.insertTemplate = new SimpleJdbcInsert(this.jdbcTemplate);
		this.insertTemplate.withTableName(TABLE_NAME).usingGeneratedKeyColumns("user_id");
	}

	private SimpleJdbcInsert insertTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HashMap<String, IRepository<?>> repositories;

	public EBUser findByUsername(String username) throws EmptyResultDataAccessException {
		String sqlQuery = "SELECT * FROM users INNER JOIN authorities ON users.username = authorities.fk_username WHERE users.username = ? AND deleted != 1";
		EBUser user = new EBUser();
		try {
			user = jdbcTemplate.query(sqlQuery, new EBUserResultSetExtractor(), new Object[] { username });
		} catch (EmptyResultDataAccessException e) {
			user = new EBUser();
		}
		return user;

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
	public List<EBUser> findByCriteria(QueryCriteria criteria, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EBUser> findAll(String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(QueryCriteria criteria, String sessionToken) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EBUser findOne(long id, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(EBUser updated, String sessionToken) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long create(EBUser newInstance, String sessionToken) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int entityId, String sessionToken) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateEntity(EBUser entity) throws Exception {
		// TODO Auto-generated method stub

	}

}
