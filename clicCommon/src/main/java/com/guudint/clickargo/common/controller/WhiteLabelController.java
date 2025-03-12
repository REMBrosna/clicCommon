package com.guudint.clickargo.common.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.common.service.impl.CkWhitelabelService;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

@RequestMapping(value = "/api/v1/clickargo/whitelabel")
@CrossOrigin
@RestController
public class WhiteLabelController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(WhiteLabelController.class);

	@Autowired
	private CkWhitelabelService wlService;

	@GetMapping("/{name}")
	public ResponseEntity<Object> getImage(@PathVariable String name) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			return ResponseEntity.ok(wlService.getBackgroundImage(name));
		} catch (Exception e) {
			log.error("getImage", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}

	}
}
