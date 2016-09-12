package com.ezbudget.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezbudget.filter.Filter;
import com.ezbudget.filter.FilterConverter;
import com.ezbudget.filter.QueryCriteria;

@Component
public class HttpUtils {

	@Autowired
	private FilterConverter filterParser;

	public QueryCriteria getQueryCriteria(HttpServletRequest request) {
		List<Filter> filters = getFilters(request);
		QueryCriteria criteria = new QueryCriteria();
		criteria.setFilters(filters);
		criteria.setSorting(request.getParameterValues("sortBy"));
		criteria.setPaging(
				(request.getParameter("pageCount") != null) ? Integer.valueOf(
						request.getParameter("pageCount")).intValue()
						: QueryCriteria.MAX_SEARCH_RESUTLS,
				(request.getParameter("pageIndex") != null) ? Integer.valueOf(
						request.getParameter("pageIndex")).intValue() : 0);

		return criteria;
	}

	private List<Filter> getFilters(HttpServletRequest servletRequest) {
		String[] _filters = servletRequest.getParameterValues("filterBy");
		if (_filters == null) {
			return new ArrayList<Filter>();
		}
		List<Filter> filters = new ArrayList<Filter>(_filters.length);
		for (String filter : _filters) {
			filters.add(filterParser.convert(filter));
		}
		return filters;
	}
}
