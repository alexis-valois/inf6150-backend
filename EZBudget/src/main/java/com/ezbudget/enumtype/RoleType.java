package com.ezbudget.enumtype;

public enum RoleType {
	USER("USER"), ADMIN("ADMIN");

	private String role;

	RoleType(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return this.role;

	}

	public static RoleType fromString(String text) {
		if (text != null) {
			for (RoleType role : RoleType.values()) {
				if (text.equalsIgnoreCase(role.toString())) {
					return role;
				}
			}
		}
		return null;
	}
}
