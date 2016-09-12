package com.ezbudget.exception;

public class WrongActivationTokenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String activationToken;

	public WrongActivationTokenException(String username, String activationToken) {
		this.username = username;
		this.activationToken = activationToken;
	}

	@Override
	public String toString() {
		return "[The activationToken does not match the one persisted : "
				+ this.username + this.activationToken + "] ";
	}

	@Override
	public String getMessage() {
		return toString() + super.getMessage();
	}
}
