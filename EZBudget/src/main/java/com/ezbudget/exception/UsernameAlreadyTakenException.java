package com.ezbudget.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;

	public UsernameAlreadyTakenException(String username, Exception e) {
		super(e);
		this.username = username;
	}

	@Override
	public String toString() {
		return "[The username is already taken : " + this.username + "] ";
	}

	@Override
	public String getMessage() {
		return toString() + super.getMessage();
	}
}
