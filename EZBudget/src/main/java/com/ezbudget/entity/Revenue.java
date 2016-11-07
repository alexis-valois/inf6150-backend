package com.ezbudget.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Revenue {
	
	
	 private long id;
	 private DateTime created;
	 private long userId;
	 private Money amount;
	 private long accountId;
	 private String frequency;
	 private DateTime starting;
	 private DateTime ending;
	 private Boolean deleted;
	 
	 
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
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public DateTime getStarting() {
		return starting;
	}
	public void setStarting(DateTime starting) {
		this.starting = starting;
	}
	public DateTime getEnding() {
		return ending;
	}
	public void setEnding(DateTime ending) {
		this.ending = ending;
	}
	 
    public Boolean getDeleted() {
			return deleted;
	}
		
    public void setDeleted(Boolean deleted) {
			this.deleted = deleted;
	}
	 
	

}
