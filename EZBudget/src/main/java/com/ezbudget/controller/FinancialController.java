package com.ezbudget.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezbudget.annotation.Access;
import com.ezbudget.entity.Account;
import com.ezbudget.enumtype.RoleType;
import com.ezbudget.filter.SubEntity;
import com.ezbudget.repository.AccountRepository;
import com.ezbudget.service.SubEntityService;
import com.ezbudget.utils.HttpUtils;

@Access(role = RoleType.USER)
@RestController
@RequestMapping({ "/rest/finance" })
public class FinancialController {

	private static Logger logger = LoggerFactory.getLogger(FinancialController.class);

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private HttpUtils httpUtils;

	@Autowired
	private SubEntityService subEntityService;

	@RequestMapping(method = { RequestMethod.GET }, value = { "/solde/{accountId}" })
	@ResponseBody
	ResponseEntity<JSONObject> getSolde(@RequestHeader(value = "sessionToken") String sessionToken,
			@PathVariable("accountId") int accountId, HttpServletRequest request,
			@RequestParam(value = "date") String dateString) {

		DateTime queryDate = DateTime.parse(dateString);
		JSONObject rtn = new JSONObject();
		try {
			Money solde = accountRepo.getSolde(sessionToken, accountId, queryDate);
			rtn.put("accountId", accountId);
			rtn.put("solde", solde.getAmount());
			return new ResponseEntity<JSONObject>(rtn, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/solde" })
	@ResponseBody
	ResponseEntity<JSONArray> getAllSoldes(@RequestHeader(value = "sessionToken") String sessionToken,
			HttpServletRequest request, @RequestParam(value = "date") String dateString) {
		List<SubEntity> subEntitiesRequirements = httpUtils.getSubEntitiesRequirements(request);
		DateTime queryDate = DateTime.parse(dateString);
		JSONArray rtn = new JSONArray();
		try {
			List<Account> accounts = accountRepo.findAll(sessionToken);
			for (Account acc : accounts) {
				JSONObject soldeObj = new JSONObject();
				Money solde = accountRepo.getSolde(sessionToken, acc.getId(), queryDate);
				soldeObj.put("accountId", acc.getId());
				soldeObj.put("solde", solde.getAmount());
				rtn.put(soldeObj);
			}
			subEntityService.insertSubEntities(rtn, subEntitiesRequirements, sessionToken);
			return new ResponseEntity<JSONArray>(rtn, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<JSONArray>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
