package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ezbudget.entity.Account;
import com.ezbudget.enumtype.AccountType;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setId(rs.getLong("id"));
		account.setType(AccountType.fromString(rs.getString("type")));
		account.setAccountName(rs.getString("name"));
		account.setUserId(rs.getLong("userId"));
		return account;
	}

}
