package com.ezbudget.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This user cannot log in to the system")
public class UserPrivilegesException extends RuntimeException {
	private static final long serialVersionUID = 122743854341486226L;
	private String username;

	public UserPrivilegesException(String username, Exception e) {
		super(e);
		this.username = username;
	}

	@Override
	public String toString() {
		return "[Could not find entity=" + this.username + "] ";
	}

	@Override
	public String getMessage() {
		return toString() + super.getMessage();
	}
}
