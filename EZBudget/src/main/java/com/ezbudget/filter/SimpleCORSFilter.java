package com.ezbudget.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {
	private static final String ALLOWED_METHODS = "POST, GET, DELETE, PUT, OPTIONS";
	private static final String MAX_AGE = "3600";
	private static final String ALLOWED_HEADERS = "x-requested-with, content-type, sessionToken, username, password";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:9000");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
		response.setHeader("Access-Control-Max-Age", MAX_AGE);
		response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
}
