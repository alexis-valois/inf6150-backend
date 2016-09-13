package com.ezbudget.filter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QueryCriteria {
	private static Logger logger = LoggerFactory.getLogger(QueryCriteria.class);

	public static int PAGING_ALL = -1;
	public static int MAX_SEARCH_RESUTLS = 1000;
	private List<Filter> filters;
	private String[] sortParams;
	private int pagingIndex = 0;
	private int pagingCount = MAX_SEARCH_RESUTLS;

	public QueryCriteria() {
		this.filters = new ArrayList<Filter>();
		this.sortParams = new String[0];
	}

	public boolean isNull() {
		return ((this.filters.size() == 0) && (this.sortParams.length == 0));
	}

	public int filterCount() {
		return this.filters.size();
	}

	public List<Filter> getFilters() {
		return this.filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public QueryCriteria andEquals(String key, String value) {
		this.filters.add(new Filter(key, FilterConditionType.EQUAL
				.getCondition(), value));
		return this;
	}

	public QueryCriteria andIsNull(String key) {
		this.filters.add(new Filter(key, FilterConditionType.IS_NULL
				.getCondition(), ""));
		return this;
	}

	public QueryCriteria andIsNotNull(String key) {
		this.filters.add(new Filter(key, FilterConditionType.IS_NOT_NULL
				.getCondition(), ""));
		return this;
	}

	public QueryCriteria andLessThanOrEqual(String key, String value) {
		this.filters.add(new Filter(key, FilterConditionType.LESS_OR_EQUAL
				.getCondition(), value));
		return this;
	}

	public QueryCriteria andLessThan(String key, String value) {
		this.filters.add(new Filter(key, FilterConditionType.LESS
				.getCondition(), value));
		return this;
	}

	public void setLimit(int limit) {
		this.pagingCount = limit;
	}

	public void setPaging(int limit, int startIndex) {
		this.pagingCount = limit;
		this.pagingIndex = startIndex;
	}

	public void setSorting(String[] sortParams) {
		if (sortParams != null)
			this.sortParams = sortParams;
	}

	public String[] getSorting() {
		return this.sortParams;
	}

	public int getPageIndex() {
		return this.pagingIndex;
	}

	public int getPageLimit() {
		return this.pagingCount;
	}

}
