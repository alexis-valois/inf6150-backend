package com.ezbudget.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezbudget.annotation.Access;
import com.ezbudget.converter.JSONObjectToEntityConverter;
import com.ezbudget.entity.EBUser;
import com.ezbudget.enumtype.RoleType;
import com.ezbudget.exception.UserPrivilegesException;
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

	@Autowired
	private JSONObjectToEntityConverter jsonObjectToEntityConverter;

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

	@RequestMapping(method = { RequestMethod.GET }, value = { "/activate" })
	@ResponseBody
	ResponseEntity<JSONObject> activate(@RequestParam(value = "activationToken") String activationToken,
			@RequestParam(value = "username") String username) {
		JSONObject rtn = new JSONObject();

		try {
			authService.activate(activationToken, username);
			rtn.put("status", "activation successful");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<JSONObject>(rtn, HttpStatus.OK);

	}

	@Access(role = RoleType.USER)
	@RequestMapping(method = { RequestMethod.POST }, value = { "/logout" })
	@ResponseBody
	ResponseEntity<JSONObject> logout(@RequestHeader(required = false, value = "sessionToken") String sessionToken) {
		JSONObject rtn = new JSONObject();
		boolean loggedOut = false;
		try {
			authService.deauthenticate(sessionToken);
			loggedOut = true;
		} catch (UserPrivilegesException priv) {
			logger.error(priv.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}
		rtn.put("LoggedOut", loggedOut);
		return new ResponseEntity<JSONObject>(rtn, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.PUT }, value = { "/register" }, headers = {
			"content-type!=multipart/form-data" })
	@ResponseBody
	ResponseEntity<JSONObject> register(@RequestBody JSONObject newUser) {
		JSONObject rtn = new JSONObject();
		try {
			newUser.put("entityType", "user");
			EBUser user = (EBUser) jsonObjectToEntityConverter.convert(newUser);
			authService.register(user);
			rtn.put("status", "registration successful");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<JSONObject>(rtn, HttpStatus.OK);
	}
}
