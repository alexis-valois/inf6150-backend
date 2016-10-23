package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.ezbudget.entity.Categorie;

public class CategorieRowMapper implements RowMapper<Categorie> {

	@Override
	public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
		Categorie categorie = new Categorie();
		categorie.setId(rs.getLong("categories_id"));
		categorie.setCategorieName(rs.getString("name"));
		categorie.setCreated(new DateTime(rs.getTimestamp("created")));
		categorie.setUserId(rs.getLong("userId"));
		return categorie;
	}

}
