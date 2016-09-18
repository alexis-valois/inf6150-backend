package com.ezbudget.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezbudget.entity.EBAuthority;
import com.ezbudget.entity.EBUser;
import com.ezbudget.enumtype.RoleType;
import com.ezbudget.filter.QueryCriteria;
import com.ezbudget.repository.EBAuthorityRepository;
import com.ezbudget.repository.EBUserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private EBAuthorityRepository authRepository;

	@Autowired
	private EBUserRepository userRepository;

	public void activate(String activationToken, String username) throws Exception {
		userRepository.activate(activationToken, username);
	}

	public void register(EBUser newUser) throws Exception {
		UUID uuid = UUID.randomUUID();
		String activationToken = uuid.toString();
		newUser.setActivationToken(activationToken);
		newUser.setDateCreated(new DateTime());
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		long userId = userRepository.create(newUser, activationToken);

		EBAuthority authority = new EBAuthority();
		authority.setAuthority(RoleType.USER);
		authority.setUsername(newUser.getUsername());

		authRepository.register(authority);

		mailService.sendActivationMail(newUser);
	}

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
