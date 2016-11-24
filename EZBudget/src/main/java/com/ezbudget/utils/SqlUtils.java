package com.ezbudget.utils;

import java.util.List;

import com.ezbudget.filter.Filter;
import com.ezbudget.filter.QueryCriteria;

public class SqlUtils {

	public static String getFetchByQueryCriteriaSqlQuery(String entityName, QueryCriteria criteria,
			boolean deletableEntity) {
		List<Filter> filters = criteria.getFilters();
		int size = criteria.getPageLimit();
		int index = criteria.getPageIndex();
		String[] sortParams = criteria.getSorting();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(entityName).append(" WHERE ");
		for (Filter filter : filters) {
			sb.append(filter.toSql());
			sb.append(" AND ");
		}

		if (!deletableEntity) {
			if (!filters.isEmpty()) {
				sb.delete(sb.lastIndexOf("AND"), sb.length());
			} else {
				sb.delete(sb.lastIndexOf("WHERE"), sb.length());
			}
		} else {
			sb.append(entityName);
			sb.append(".deleted != 1");
		}

		sb.append(getOrderBySqlQuery(entityName, sortParams)).append(getLimitSqlQuery(size, index));

		return sb.toString();
	}

	public static String insertJointToSql(String intialSql, String jointStatement) {
		StringBuilder sb = new StringBuilder();
		sb.append(intialSql);
		int insertIdx = sb.indexOf("WHERE");
		sb.insert(insertIdx, " " + jointStatement + " ");
		return sb.toString();
	}

	public static String getLimitSqlQuery(int limit, int offset) {
		if (limit < 1) {
			return "";
		}
		return new StringBuilder().append(" LIMIT ").append(offset).append(",").append(limit).toString();
	}

	public static String getOrderBySqlQuery(String entityName, String[] sortParams) {
		StringBuilder sb = new StringBuilder();
		for (String s : sortParams) {
			sb.append(" ").append(entityName).append(".").append(s).append(",");
		}
		if ((sortParams.length > 0) && (sb.length() > 0)) {
			sb.insert(0, " ORDER BY");
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	}

	public static String getCountSqlQuery(String entityname, QueryCriteria criteria, boolean deletableEntity) {
		String sql = getFetchByQueryCriteriaSqlQuery(entityname, criteria, deletableEntity);
		return sql.replace("*", "count(*)");
	}
}
