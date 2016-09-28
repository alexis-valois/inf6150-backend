package com.ezbudget.enumtype;

public enum AccountType {
	CR("CREDITOR"), DT("DEBITOR");

	private String role;

	AccountType(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return this.role;

	}

	public static AccountType fromString(String text) {
		if (text != null) {
			for (AccountType role : AccountType.values()) {
				if (text.equalsIgnoreCase(role.toString())) {
					return role;
				}
			}
		}
		return null;
	}
}
