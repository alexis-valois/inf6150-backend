package com.ezbudget.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

import com.ezbudget.enumtype.AccountType;

public class Account implements IEntity {

	private long id;
	private AccountType type;
	private String accountName;
	private DateTime created;
	private Money initAmount;
	private long userId;

	public Money getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(Money initAmount) {
		this.initAmount = initAmount;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public boolean isEmpty() {
		return this.accountName == null;
	}

}
