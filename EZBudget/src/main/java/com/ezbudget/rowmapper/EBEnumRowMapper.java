package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ezbudget.entity.EBEnum;

public class EBEnumRowMapper implements RowMapper<EBEnum> {

	@Override
	public EBEnum mapRow(ResultSet rs, int rowNum) throws SQLException {
		EBEnum ebEnum = new EBEnum();
		ebEnum.setId(rs.getLong("id"));
		ebEnum.setKey(rs.getString("enum_key"));
		ebEnum.setValue(rs.getString("enum_value"));
		return ebEnum;
	}

}
