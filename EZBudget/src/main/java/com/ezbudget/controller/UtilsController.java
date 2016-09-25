package com.ezbudget.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezbudget.annotation.Access;
import com.ezbudget.entity.EBUser;
import com.ezbudget.repository.EBUserRepository;

@RestController
@RequestMapping({ "/utils" })
public class UtilsController {

	private static Logger logger = LoggerFactory.getLogger(UtilsController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EBUserRepository userRepository;

	@Access(role = "USER")
	@RequestMapping(method = { RequestMethod.GET }, value = { "/hasher/{rawPassword}" })
	@ResponseBody
	JSONObject generateBCryptPassword(@PathVariable("rawPassword") String rawPassword, HttpServletRequest request) {
		JSONObject rtn = new JSONObject();
		rtn.put("hash", passwordEncoder.encode(rawPassword));
		return rtn;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/auth/{username}/{rawPassword}" })
	@ResponseBody
	ResponseEntity<JSONObject> authenticationTest(@PathVariable("username") String username,
			@PathVariable("rawPassword") String rawPassword, HttpServletRequest request) {
		EBUser user = userRepository.findByUsername(username);
		String encodedPassword = user.getPassword();
		if (passwordEncoder.matches(rawPassword, encodedPassword)) {
			JSONObject rtn = new JSONObject();
			rtn.put("Authentication", "Success");
			return new ResponseEntity<JSONObject>(rtn, HttpStatus.OK);
		}
		return new ResponseEntity<JSONObject>(new JSONObject().put("Authentication", "Failed"), HttpStatus.FORBIDDEN);
	}
}
