package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import com.ezbudget.entity.Revenue;


public class RevenuesRowMapper implements RowMapper<Revenue>{
	

	
	@Override
	public Revenue mapRow(ResultSet rs, int rowNum) throws SQLException {
		CurrencyUnit currency = CurrencyUnit.of(rs.getString("currency"));
		Revenue revenue = new Revenue();
		revenue.setId(rs.getLong("id"));
		revenue.setFrequency(rs.getString("frequency")); 
		revenue.setAmount(Money.of(currency, rs.getBigDecimal("amount")));
		revenue.setCreated(new DateTime(rs.getTimestamp("created"))); 
		revenue.setStarting(new DateTime(rs.getTimestamp("starting"))); 
		revenue.setEnding(new DateTime(rs.getTimestamp("ending"))); 
		revenue.setUserId(rs.getLong("userId"));
		revenue.setAccountId(rs.getLong("accountId"));
		return revenue;
	}

}
