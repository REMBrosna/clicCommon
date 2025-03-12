package com.guudint.clickargo.estamp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.estamp.dto.StampCallbackRequest;
import com.guudint.clickargo.estamp.dto.StampRequest;
import com.guudint.clickargo.external.services.IStampGateway;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

@RequestMapping(value = "/api/v1/clickargo/estamp")
@CrossOrigin
@RestController
public class EstampController {
    
    private static Logger log = Logger.getLogger(EstampController.class);

    @Autowired
    private IStampGateway iStampGateway;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody StampRequest request) {
        log.debug("create");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            iStampGateway.stampDocument(request);
            return new ResponseEntity<Object>("OK", HttpStatus.OK);
        } catch (Exception e) {
            log.error("create", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public ResponseEntity<Object> callback(@RequestBody StampCallbackRequest request) {
        log.debug("callback");
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            return new ResponseEntity<Object>(iStampGateway.stampCallback(request), HttpStatus.OK);
        } catch (Exception e) {
            log.error("callback", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
