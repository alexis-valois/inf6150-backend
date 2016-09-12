package com.ezbudget.filter;

import java.util.Arrays;

public class Filter {
	private String key;
	private String condition;
	private String[] values;
	public static String FILTER_SEPARATOR = "\\[\\]";
	public static String FILTER_INNER_SEPARATOR = ";";
	public static String FILTER_VALUE_SEPARATOR = ",";

	public Filter(String key, String condition, String value) {
		this.key = key;
		this.condition = condition;
		this.values = new String[] { value };
	}

	public Filter(String key, String condition, String[] values) {
		this.key = key;
		this.condition = condition;
		this.values = values;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String[] getValues() {
		return this.values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String toSql() {
		StringBuilder rtn = new StringBuilder();
		switch (this.condition) {
		case "gt":
			rtn.append(this.key).append(" > '").append(this.values[0])
					.append("'");
			break;
		case "ge":
			rtn.append(this.key).append(" >= '").append(this.values[0])
					.append("'");
			break;
		case "l":
			rtn.append("");
			break;
		case "le":
			rtn.append(this.key).append(" <= '").append(this.values[0])
					.append("'");
			break;
		case "bt":
			rtn.append(this.key).append(" BETWEEN '").append(this.values[0])
					.append("' AND '").append(this.values[1]).append("'");
			break;
		case "in":
			rtn.append(this.key).append(" IN(");
			String[] values = this.values;
			int len = values.length;
			for (int i = 0; i < len; ++i) {
				rtn.append("'").append(values[i]).append("',");
			}
			rtn.deleteCharAt(rtn.lastIndexOf(","));
			rtn.append(")");
			break;
		case "nin":
			rtn.append(this.key);
			rtn.append(" NOT IN( ");
			String[] nValues = this.values;
			int nLen = nValues.length;
			for (int i = 0; i < nLen; ++i) {
				rtn.append("'").append(nValues[i]).append("',");
			}
			rtn.deleteCharAt(rtn.lastIndexOf(","));
			rtn.append(")");
			break;
		case "eq":
			rtn.append(this.key).append(" = '").append(this.values[0])
					.append("'");
			break;
		case "neq":
			rtn.append(this.key).append(" <> '").append(this.values[0])
					.append("'");
			break;
		case "lk":
			rtn.append(this.key).append(" LIKE '%").append(this.values[0])
					.append("%'");
			break;
		case "nlk":
			rtn.append(this.key).append(" NOT LIKE '%").append(this.values[0])
					.append("%' ");
			break;
		case "lt":
			rtn.append(this.key).append(" < '").append(this.values[0])
					.append("'");
			break;
		case "sw":
			rtn.append(this.key).append(" LIKE '").append(this.values[0])
					.append("%'");
			break;
		case "ew":
			rtn.append(this.key).append(" LIKE '%").append(this.values[0])
					.append("'");
			break;
		case "null":
			rtn.append(this.key).append(" IS NULL");
			break;
		case "notnull":
			rtn.append(this.key).append(" IS NOT NULL");
			break;
		case "ln":
			rtn.append(" (").append(this.key).append(" < '")
					.append(this.values[0]).append("'").append(" OR ")
					.append(this.key).append(" is null ").append(") ");
			rtn.append("");
			break;
		case "gn":
			rtn.append("");
			break;
		default:
			rtn.append("");
			break;
		}
		return rtn.toString();
	}

	@Override
	public String toString() {
		StringBuilder rtn = new StringBuilder();
		rtn.append(this.key).append(" ").append(this.condition).append(" ")
				.append(Arrays.toString(this.values));
		return rtn.toString();
	}
}
