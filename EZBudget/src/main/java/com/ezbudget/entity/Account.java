package com.ezbudget.entity;

import com.ezbudget.enumtype.AccountType;

public class Account implements IEntity {

	private int id;
	private AccountType type;
	private String accountName;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		// TODO Auto-generated method stub
		return false;
	}

}
