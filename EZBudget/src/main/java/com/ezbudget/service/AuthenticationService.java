package com.ezbudget.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezbudget.entity.EBUser;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.repository.EBUserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EBUserRepository userRepository;

	public EBUser authenticate(String username, String password) throws Exception {
		EBUser user = userRepository.findByUsername(username);
		String sessionToken = "";
		if (this.checkPassword(username, password)) {
			UUID uuid = UUID.randomUUID();
			sessionToken = uuid.toString();
			user.setSessionToken(sessionToken);
			user.setLastLogin(new DateTime());
			userRepository.performLogin(user);
			user.setPassword(null);
			user.setActivationToken(null);
			QueryCriteria criteria = new QueryCriteria();
			criteria.andEquals("fk_user_id", Long.toString(user.getId()));
			return user;
		} else {
			throw new RuntimeException("Wrong password.");
		}
	}

	public void deauthenticate(String sessionToken) throws Exception {
		EBUser user = userRepository.findBySessionToken(sessionToken);
		user.setSessionToken(null);
		user.setLastLogout(new DateTime());
		userRepository.performLogout(user, sessionToken);

	}

	public boolean checkPassword(String username, String password) {
		EBUser user = userRepository.findByUsername(username);
		String savedHash = user.getPassword();
		return BCrypt.checkpw(password, savedHash);
	}
}
