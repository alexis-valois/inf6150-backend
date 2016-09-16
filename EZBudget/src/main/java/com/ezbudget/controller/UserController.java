package com.ezbudget.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezbudget.entity.EBUser;
import com.ezbudget.service.AuthenticationService;
import com.ezbudget.web.RestRessourceAssembler;

@RestController
@RequestMapping({ "/user" })
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RestRessourceAssembler assembler;

	@Autowired
	private AuthenticationService authService;

	@RequestMapping(method = { RequestMethod.POST }, value = { "/login" })
	@ResponseBody
	ResponseEntity<JSONObject> login(@RequestHeader("username") String username,
			@RequestHeader("password") String password) {
		JSONObject rtn = new JSONObject();
		try {
			EBUser user = authService.authenticate(username, password);
			rtn = this.assembler.getJSONResource(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<JSONObject>(rtn, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST }, value = { "/logout" })
	@ResponseBody
	ResponseEntity<JSONObject> logout(@RequestHeader(required = false, value = "sessionToken") String sessionToken) {
		JSONObject rtn = new JSONObject();
		boolean loggedOut = false;
		try {
			authService.deauthenticate(sessionToken);
			loggedOut = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}
		rtn.put("LoggedOut", loggedOut);
		return new ResponseEntity<JSONObject>(rtn, HttpStatus.OK);
	}
}
