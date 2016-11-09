package com.ezbudget.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Revenue implements IEntity {
	
	
	 private long id;
	 private DateTime created;
	 private long userId;
	 private Money amount;
	 private long accountId;
	 private String frequency;
	 private DateTime revStarting;
	 private DateTime revEnding;
	 private boolean deleted;
	 
	 
	 
	
	
	
	public DateTime getRevStarting() {
		return revStarting;
	}
	public void setRevStarting(DateTime revStarting) {
		this.revStarting = revStarting;
	}
	public DateTime getRevEnding() {
		return revEnding;
	}
	public void setRevEnding(DateTime revEnding) {
		this.revEnding = revEnding;
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
	
	 
    public boolean getDeleted() {
			return deleted;
	}
		
    public void setDeleted(boolean deleted) {
			this.deleted = deleted;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	 
	

}
