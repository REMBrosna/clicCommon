package com.guudint.clickargo.clicservice.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.guudint.clickargo.clicservice.dto.CkSvcSub;
import com.guudint.clickargo.clicservice.service.impl.CkSvcSerivce;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;
import com.vcc.camelone.util.PrincipalUtilService;

@RequestMapping(value = "/api/v1/clickargo/svc")
@CrossOrigin
@Controller
public class CkSvcController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkSvcController.class);

	@Autowired
	private CkSvcSerivce svcSerivce;

	@Autowired
	private PrincipalUtilService principalUtilService;

	@GetMapping(value = "/findSubscribedAppSvc")
	public ResponseEntity<Object> findSubscribedAppSvc() {

		ServiceStatus serviceStatus = new ServiceStatus();

		try {
			List<CkSvcSub> svcSubList = svcSerivce
					.findSubscribedAppSvc(principalUtilService.getPrincipal().getUserAccnId());

			ObjectMapper objMapper = new ObjectMapper();
			objMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objMapper.setSerializationInclusion(Include.NON_NULL);
			return ResponseEntity.ok(objMapper.writeValueAsString(svcSubList));
		} catch (Exception ex) {
			log.error("findSubscribedAppSvc", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Loads all services with indicator for subscribed by the account
	 */
	@GetMapping(value = "/subscriptions")
	public ResponseEntity<Object> loadAuthorizedServices() {

		ServiceStatus serviceStatus = new ServiceStatus();

		try {
			List<CkMstServiceType> svcSubList = svcSerivce
					.getServices(principalUtilService.getPrincipal().getUserAccnId());

			ObjectMapper objMapper = new ObjectMapper();
			objMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objMapper.setSerializationInclusion(Include.NON_NULL);
			return ResponseEntity.ok(objMapper.writeValueAsString(svcSubList));
		} catch (Exception ex) {
			log.error("authorizedParties", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Loads all services with indicator for subscribed by the account
	 */
	@GetMapping(value = "/")
	public ResponseEntity<Object> loadActiveServices() {

		ServiceStatus serviceStatus = new ServiceStatus();

		try {
			List<CkMstServiceType> svcSubList = svcSerivce.getActiveServices();

			ObjectMapper objMapper = new ObjectMapper();
			objMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objMapper.setSerializationInclusion(Include.NON_NULL);
			return ResponseEntity.ok(objMapper.writeValueAsString(svcSubList));
		} catch (Exception ex) {
			log.error("authorizedParties", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
}
