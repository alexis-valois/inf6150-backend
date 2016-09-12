package com.ezbudget.entity;

import javax.validation.constraints.NotNull;

import com.ezbudget.enumtype.RoleType;

public class EBAuthority implements IEntity {

	private long authorityId = 0;

	@NotNull
	private RoleType authority = null;

	private String username = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(long authorityId) {
		this.authorityId = authorityId;
	}

	public RoleType getAuthority() {
		return authority;
	}

	public void setAuthority(RoleType authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + (int) (authorityId ^ (authorityId >>> 32));
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EBAuthority other = (EBAuthority) obj;
		if (authority != other.authority)
			return false;
		if (authorityId != other.authorityId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public boolean isEmpty() {
		return (authority == null && authorityId == 0 && username.isEmpty());
	}

}
