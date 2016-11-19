package com.ezbudget.filter;

public class SubEntity {

	public static final String SUBENTITY_INNER_SEPARATOR = ";";
	private String entityName;
	private String foreignKeyName;

	public SubEntity(String type, String foreignKeyName) {
		this.entityName = type;
		this.foreignKeyName = foreignKeyName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getForeignKeyName() {
		return foreignKeyName;
	}

	public void setForeignKeyName(String foreignKeyName) {
		this.foreignKeyName = foreignKeyName;
	}

}
