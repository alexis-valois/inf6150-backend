package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;


import com.ezbudget.entity.Bill;

public class BillsRowMapper implements RowMapper<Bill>{

	@Override
	public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
		CurrencyUnit currency = CurrencyUnit.of(rs.getString("currency"));
		Bill bill = new Bill();
		bill.setId(rs.getLong("id"));
		bill.setCreated(new DateTime(rs.getTimestamp("created")));
		bill.setUserId(rs.getLong("userId"));
		bill.setAmount(Money.of(currency, rs.getBigDecimal("amount")));
		bill.setCategorieId(rs.getLong("categorieId"));
		bill.setSupplierId(rs.getLong("supplierId"));
		bill.setAccountId(rs.getLong("accountId"));
		return bill;
	}
}
