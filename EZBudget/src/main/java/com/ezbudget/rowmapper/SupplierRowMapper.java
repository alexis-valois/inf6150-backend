package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.ezbudget.entity.Supplier;

public class SupplierRowMapper implements RowMapper<Supplier> {

	@Override
	public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
		Supplier supplier = new Supplier();
		supplier.setId(rs.getLong("id"));
		supplier.setSupplierName(rs.getString("name"));
		supplier.setUserId(rs.getLong("userId"));
		supplier.setCreated(new DateTime(rs.getTimestamp("created")));
		return supplier;
	}

}
