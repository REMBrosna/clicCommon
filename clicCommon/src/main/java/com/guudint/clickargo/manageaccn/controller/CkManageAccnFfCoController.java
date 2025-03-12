package com.guudint.clickargo.manageaccn.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.guudint.clickargo.manageaccn.dto.CkAccnUser;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.manageaccn.service.CkManageAccnFfCoService;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;
import com.vcc.camelone.master.controller.PathNotFoundException;

@RequestMapping(value = "/api/v1/clickargo/manageAccnFfCo")
@CrossOrigin
@Controller
public class CkManageAccnFfCoController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkManageAccnFfCoController.class);


	@Autowired
	private CkManageAccnFfCoService ckManageAccnFfCoService;



	@RequestMapping(value = "/{accnId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAccountDetails(@PathVariable String accnId,
			@RequestBody CkAccnUser accnUser) {
		log.debug("updateAccountDetails");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			CkAccnUser opEntity = ckManageAccnFfCoService.updateCompanyAccount(accnId, accnUser);
			return ResponseEntity.ok(opEntity);
		} catch (ValidationException ex) {
			log.error("updateAccountDetails", ex);
			serviceStatus.setData(accnUser);
			serviceStatus.setStatus(STATUS.VALIDATION_FAILED);
			serviceStatus.setErr(new ServiceError(-500, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			log.error("updateAccountDetails", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createAccountDetails(@RequestBody CkAccnUser accnUser) {
		log.debug("createAccountDetails");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			CkAccnUser opEntity = ckManageAccnFfCoService.createAccountUser(accnUser);
			return ResponseEntity.ok(opEntity);
		} catch (ValidationException ex) {
			log.error("createAccountDetails", ex);
			serviceStatus.setData(accnUser);
			serviceStatus.setStatus(STATUS.VALIDATION_FAILED);
			serviceStatus.setErr(new ServiceError(-500, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			log.error("createAccountDetails", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{accnId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAccountDetails(@PathVariable String accnId) {
		log.debug("getAccountDetails");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			CkAccnUser accnUser = null;

			if (accnId.equals("-")) {
				accnUser = ckManageAccnFfCoService.getNew();
			} else {
				accnUser = ckManageAccnFfCoService.getCKAccountUser(accnId);
			}

			return ResponseEntity.ok(accnUser);

		} catch (Exception ex) {
			log.error("getAccountDetails", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/accnStatus/{accnId}/{accnStatus}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAccnStatus(@PathVariable String accnId, @PathVariable String accnStatus) {
		log.debug("updateAccnStatus");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			CoreAccnStateEnum status = CoreAccnStateEnum.getByState(accnStatus.charAt(0));
			
			ckManageAccnFfCoService.updateCompanyAccountStatus(accnId, status);


			return ResponseEntity.ok("");

		} catch (Exception ex) {
			log.error("updateAccnStatus", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/list")
	public ResponseEntity<Object> getEntitiesBy(@RequestParam Map<String, String> params) {
		log.debug("getEntitiesBy");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			Optional<Object> opEntity = ckManageAccnFfCoService.getEntitiesByProxy(params);
			return ResponseEntity.ok(opEntity.get());
		} catch (PathNotFoundException ex) {
			log.error("getEntityById", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-500, ex));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			log.error("getEntityById", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/suppDocs", method = RequestMethod.GET)
	public ResponseEntity<Object> getSupportingDocs() {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			return ResponseEntity.ok(ckManageAccnFfCoService.getSuppDocs());

		} catch (Exception ex) {
			log.error("getSupportingDocs", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

}
