package com.ezbudget.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Bill implements IEntity {

	private long id;
	private DateTime created;
	private DateTime billDate;
	private long userId;
	private Money amount;
	private long categorieId;
	private long supplierId;
	private long accountId;

	public DateTime getBillDate() {
		return billDate;
	}

	public void setBillDate(DateTime billDate) {
		this.billDate = billDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public long getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(long categorieId) {
		this.categorieId = categorieId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public boolean isEmpty() {
		return this.amount == null;
	}
}
