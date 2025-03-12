package com.guudint.clickargo.manageaccn.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.guudint.clickargo.common.enums.MstEntityTypes;
import com.guudint.clickargo.manageaccn.dto.CkManageAccn;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.manageaccn.service.CkManageAccnService;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAddress;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.master.controller.PathNotFoundException;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstCountry;

@RequestMapping(value = "/api/v1/clickargo/manageaccn")
@CrossOrigin
@Controller
public class CkManageAccnController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkManageAccnController.class);

	@Autowired
	@Qualifier("ccmAccnService")
	private IEntityService<TCoreAccn, String, CoreAccn> ccmAccnService;

	@Autowired
	private CkManageAccnService ckManageAccnService;

	@RequestMapping(value = "/{accnId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAccountDetails(@PathVariable String accnId,
			@RequestBody CkManageAccn ckManageAccn) {
		log.debug("updateAccountDetails");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			CkManageAccn opEntity = ckManageAccnService.updateCompanyAccount(accnId, ckManageAccn);
			return ResponseEntity.ok(opEntity);
		} catch (ValidationException ex) {
			log.error("updateAccountDetails", ex);
			serviceStatus.setData(ckManageAccn);
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
	public ResponseEntity<Object> createAccountDetails(@RequestBody CkManageAccn ckAccn) {
		log.debug("createAccountDetails");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			CkManageAccn opEntity = ckManageAccnService.createCompanyAccount(ckAccn);
			return ResponseEntity.ok(opEntity);
		} catch (ValidationException ex) {
			log.error("createAccountDetails", ex);
			serviceStatus.setData(ckAccn);
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

			CkManageAccn ckManageAccn = new CkManageAccn();
			CoreAccn coreAccn = new CoreAccn();
			if (accnId.equals("-")) {
				coreAccn = new CoreAccn();
				coreAccn.setAccnStatus(CoreAccnStateEnum.NEW.getCode());
				CoreContact accnContact = new CoreContact();
				accnContact.setContactEmail("");
				coreAccn.setAccnContact(accnContact);

				MstAccnType mstAccnType = new MstAccnType();
				coreAccn.setTMstAccnType(mstAccnType);

				CoreAddress accnAddr = new CoreAddress();
				MstCountry mstCountry = new MstCountry();
				mstCountry.setCtyCode("");
				accnAddr.setAddrCtry(mstCountry);
				coreAccn.setAccnAddr(accnAddr);

				ckManageAccn.setAccnDetails(coreAccn);
				ckManageAccn.setAccnProcessType(MstEntityTypes.ACCN_REGISTRATION.name());

				ckManageAccnService.loadSvcSubscriptions(accnId, ckManageAccn);
			} else {
				ckManageAccn = ckManageAccnService.getCKAccountDetails(accnId);

			}

			return ResponseEntity.ok(ckManageAccn);

		} catch (Exception ex) {
			log.error("getAccountDetails", ex);
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
			Optional<Object> opEntity = ckManageAccnService.getEntitiesByProxy(params);
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

			return ResponseEntity.ok(ckManageAccnService.getSuppDocs());

		} catch (Exception ex) {
			log.error("getSupportingDocs", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/logo/{accnId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCompanyLogoImage(@PathVariable String accnId) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			return ResponseEntity.ok(ckManageAccnService.getCompanyLogoBase64Str(accnId));
		} catch (Exception e) {
			log.error("getCompanyLogoImage", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/bglogin/{accnId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCustomBackground(@PathVariable String accnId) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			return ResponseEntity.ok(ckManageAccnService.getLoginBackgroundImageStr(accnId));
		} catch (Exception e) {
			log.error("getCompanyLogoImage", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/check/registrationNo", method = RequestMethod.POST)
	public ResponseEntity<Object> checkCompanyRegistration(@RequestBody CoreAccn accn) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			return ResponseEntity.ok(ckManageAccnService.checkCompanyRegNoUnique(accn));
		} catch (Exception e) {
			log.error("getCompanyLogoImage", e);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

}
