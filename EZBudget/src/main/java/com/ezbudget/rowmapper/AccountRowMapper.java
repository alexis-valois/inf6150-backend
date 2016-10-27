package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.ezbudget.entity.Account;
import com.ezbudget.enumtype.AccountType;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setId(rs.getLong("id"));
		account.setType(AccountType.fromString(rs.getString("type")));
		account.setInitAmount(Money.parse(rs.getString("initAmount")));
		account.setAccountName(rs.getString("name"));
		account.setCreated(new DateTime(rs.getTimestamp("created")));
		account.setUserId(rs.getLong("userId"));
		return account;
	}

}
