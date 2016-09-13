package com.ezbudget.filter;

public enum FilterConditionType {
	GREATER("gt"), GREATER_OR_EQUAL("ge"), LESS("l"), LESS_OR_EQUAL("le"), BETWEEN("bt"), IN("in"), NOT_IN(
			"nin"), EQUAL("eq"), NOT_EQUAL("neq"), LIKE("lk"), NOT_LIKE("nlk"), START_WITH("sw"), END_WITH(
					"ew"), IS_NULL("null"), IS_NOT_NULL("notnull"), LESS_OR_NULL("ln"), GREATER_OR_NULL("gn");

	private String condition = "";

	FilterConditionType(String condition) {
		this.condition = condition;
	}

	public String getCondition() {
		return this.condition;
	}

	public static boolean isTypeValid(String type) {
		try {
			FilterConditionType returnType = valueOf(type);
			return (returnType != null);
		} catch (Exception e) {
		}
		return false;
	}
}
