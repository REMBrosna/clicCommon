package com.guudint.clickargo.common.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guudint.clickargo.common.dto.CkFormControlDto;
import com.guudint.clickargo.common.dto.CkFormControlReqDto;
import com.guudint.clickargo.common.service.ICkFormControlService;
import com.guudint.clickargo.common.service.impl.CKEncryptionUtil;
import com.vcc.camelone.common.controller.entity.AbstractPortalController;
import com.vcc.camelone.common.exception.PermissionException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

@RequestMapping(value = "/api/v1/clickargo/controls")
@CrossOrigin
@Controller
public class CkFormControlsController extends AbstractPortalController {

	// Static Attribute
	//////////////////
	private static final Logger log = Logger.getLogger(CkFormControlsController.class);

	@Autowired
	private ICkFormControlService formCtrlService;

	@PostMapping(value = "/")
	public ResponseEntity<Object> fetchControls(@RequestBody CkFormControlReqDto reqDTO) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			List<CkFormControlDto> controls = formCtrlService.getControls(reqDTO, getPrincipal());
			return ResponseEntity.ok(controls);

		} catch (PermissionException ex) {
			log.error("fetchControls", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("fetchControls", ex);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/{jobId}/{encAccnId}/{encRoles}")
	public ResponseEntity<Object> fetchControls(@RequestBody CkFormControlReqDto reqDTO, @PathVariable String jobId,
			@PathVariable String encAccnId, @PathVariable String encRoles) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			String accnIdStr = CKEncryptionUtil.decrypt(encAccnId, jobId);
			String rolesStr = CKEncryptionUtil.decrypt(encRoles, jobId);
			List<CkFormControlDto> controls = formCtrlService.getControls(reqDTO, accnIdStr, rolesStr);
			return ResponseEntity.ok(controls);

		} catch (PermissionException ex) {
			log.error("fetchControls", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("fetchControls", ex);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
}
