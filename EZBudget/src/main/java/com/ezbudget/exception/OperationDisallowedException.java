package com.ezbudget.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "This method is not allowed.")
public class OperationDisallowedException extends RuntimeException {
	private static final long serialVersionUID = 122743854341486226L;
	private HttpMethod method;

	public OperationDisallowedException(HttpMethod method, Exception e) {
		super(e);
		this.method = method;
	}

	@Override
	public String toString() {
		return "[This method is not allowed=" + this.method + "] ";
	}

	@Override
	public String getMessage() {
		return toString() + super.getMessage();
	}
}
