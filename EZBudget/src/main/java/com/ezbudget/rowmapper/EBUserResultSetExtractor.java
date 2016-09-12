package com.ezbudget.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.ezbudget.entity.EBAuthority;
import com.ezbudget.entity.EBUser;
import com.ezbudget.enumtype.RoleType;

public class EBUserResultSetExtractor implements ResultSetExtractor<EBUser> {

	private void extractAuthority(ResultSet rs, EBUser user)
			throws SQLException {
		EBAuthority authority = new EBAuthority();
		authority.setAuthorityId(rs.getInt("authorities_id"));
		authority.setAuthority(RoleType.fromString(rs.getString("authority")));
		user.addAuthority(authority);
	}

	@Override
	public EBUser extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		EBUser user = new EBUser();
		while (rs.next()) {
			if (rs.getRow() == 1) {
				user.setId(rs.getLong("user_id"));
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setPassword(rs.getString("password"));
				user.setDeleted(rs.getBoolean("deleted"));
				user.setEnabled(rs.getBoolean("enabled"));
				user.setDateCreated(new DateTime(rs
						.getTimestamp("date_created")));
				if (rs.getTimestamp("last_login") != null) {
					user.setLastLogin(new DateTime(rs
							.getTimestamp("last_login")));
				}
				if (rs.getTimestamp("last_logout") != null) {
					user.setLastLogout(new DateTime(rs
							.getTimestamp("last_logout")));
				}
				user.setLocked(rs.getBoolean("locked"));
				user.setCredentialsExpired(rs.getBoolean("credentials_expired"));
				user.setAccountExpired(rs.getBoolean("account_expired"));
				user.setSessionToken(rs.getString("session_token"));
				user.setEmail(rs.getString("email"));
				user.setActivationToken(rs.getString("activation_token"));
				extractAuthority(rs, user);
			} else {
				extractAuthority(rs, user);
			}
		}

		return user;
	}
}
