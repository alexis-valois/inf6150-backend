package com.ezbudget.entity;

import org.joda.time.DateTime;

public class Categorie implements IEntity {

	private long id;
	private String name;
	private DateTime created;
	private long userId;

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


	public String getCategorieName() {
		return name;
	}

	public void setCategorieName(String categorieName) {
		this.name = categorieName;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
