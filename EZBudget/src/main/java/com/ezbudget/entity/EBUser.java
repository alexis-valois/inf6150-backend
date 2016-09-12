package com.ezbudget.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

public class EBUser implements IEntity {

	private long id;

	@NotNull
	private String username;

	@NotNull
	private String email;

	private String activationToken;

	private String firstName;
	private String lastName;

	@NotNull
	private String password;

	private boolean enabled = true;

	private boolean deleted;

	private DateTime dateCreated;

	private DateTime lastLogin;

	private DateTime lastLogout;

	private boolean locked;

	private boolean credentialsExpired;

	private boolean accountExpired;

	private String sessionToken;
	
	private Set<EBAuthority> authorities = new HashSet<EBAuthority>();

	public Set<EBAuthority> getAuthorities() {
		return authorities;
	}
	
	public void addAuthority(EBAuthority authority) {
		this.authorities.add(authority);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public DateTime getLastLogout() {
		return lastLogout;
	}

	public void setLastLogout(DateTime lastLogout) {
		this.lastLogout = lastLogout;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public DateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(DateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public DateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(DateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public boolean isEmpty() {
		return (activationToken == null && dateCreated == null && firstName == null && lastName == null
				&& lastLogin == null && lastLogout == null && password == null && sessionToken == null);
	}

}
