package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.ezbudget.entity.Account;
import com.ezbudget.enumtype.AccountType;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		CurrencyUnit currency = CurrencyUnit.of(rs.getString("currency"));
		Account account = new Account();
		account.setId(rs.getLong("id"));
		account.setType(AccountType.fromString(rs.getString("type")));
		account.setInitAmount(Money.of(currency, rs.getBigDecimal("initAmount")));
		account.setAccountName(rs.getString("name"));
		account.setCreated(new DateTime(rs.getTimestamp("created")));
		account.setUserId(rs.getLong("userId"));
		return account;
	}

}
