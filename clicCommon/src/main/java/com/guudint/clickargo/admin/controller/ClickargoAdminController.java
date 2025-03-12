package com.guudint.clickargo.admin.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.admin.dto.ClickargoUser;
import com.guudint.clickargo.admin.service.ClickargoManageUserService;
import com.guudint.clickargo.common.dto.CkAccn;
import com.guudint.clickargo.common.service.impl.CkCoreAccnService;
import com.guudint.clickargo.controller.CustomSerializerProvider;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.controller.PathNotFoundException;
import com.vcc.camelone.common.controller.entity.AbstractPortalController;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.PermissionException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;
import com.vcc.camelone.core.model.TCoreSession;

@RequestMapping(value = "/api/v1/clickargo/admin/user")
@CrossOrigin
@RestController
public class ClickargoAdminController extends AbstractPortalController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(ClickargoAdminController.class);

	@Autowired
	private ClickargoManageUserService clickargoUserService;

	@Autowired
	private CkCoreAccnService coreAccnService;

	protected ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void configureObjectMapper() {
		objectMapper.setSerializerProvider(new CustomSerializerProvider());
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ResponseEntity<Object> newUser() {
		log.debug("newUser");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			ClickargoUser opEntity = clickargoUserService.getNewUser();
			return ResponseEntity.ok(objectMapper.writeValueAsString(opEntity));

		} catch (PermissionException ex) {
			log.error("newUser", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("newUser", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{usrId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getPortalUser(@PathVariable String usrId) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			if (StringUtils.isEmpty(usrId))
				throw new Exception("param usrId empty or null");

			Principal principal = getPrincipal();
			if (null == principal)
				throw new ProcessingException("principal is null");

			String encryptedUsrId = usrId.equalsIgnoreCase("profile") ? principal.getUserId()
					: new String(Base64.getDecoder().decode(usrId));

			if (encryptedUsrId.equals("0") || encryptedUsrId.equals("-")) {
				return newUser();
			}

			ClickargoUser portalUser = clickargoUserService.getUser(encryptedUsrId);
			if (null == portalUser)
				throw new Exception("protalUser not found: " + encryptedUsrId);

			List<TCoreSession> userSessions = clickargoUserService.getCoreSessionByUser(encryptedUsrId);
			if (null != userSessions && userSessions.size() > 0)
				portalUser.setLoggedIn(true);

			return ResponseEntity.ok(portalUser);

		} catch (PermissionException ex) {
			log.error("getPortalUser", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (PathNotFoundException ex) {
			log.error("createEntity", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-500, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			log.error("createEntity", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/check-id-availability", method = RequestMethod.GET)
	public ResponseEntity<Object> checkUserIdAvailability(@RequestParam String usrId) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			if (StringUtils.isEmpty(usrId))
				throw new Exception("param usrId empty or null");

			ClickargoUser portalUser;
			// If method getUser throw error EntityNotFoundException mean user not exit,
			try {
				portalUser = clickargoUserService.getUser(usrId);
			} catch (Exception ex) {
				Map<String, Object> response = new HashMap<>();
				response.put("available", true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			if (portalUser != null)
				throw new Exception("User already exists with id: " + usrId);
			return null;
		} catch (PermissionException ex) {
			log.error("checkUserIdAvailability", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("checkUserIdAvailability", ex);
			serviceStatus.setStatus(STATUS.VALIDATION_FAILED);
			serviceStatus.setErr(new ServiceError(-500, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody ClickargoUser portalUser) {
		log.debug("createUser");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			ClickargoUser opEntity = clickargoUserService.createUser(portalUser);
			return ResponseEntity.ok(objectMapper.writeValueAsString(opEntity));

		} catch (PermissionException ex) {
			log.error("createUser", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("createUser", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update/{action}/{usrId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUserStatus(@PathVariable String action, @PathVariable String usrId) {
		log.debug("updateUserStatus");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			if (StringUtils.isBlank(action))
				throw new ParameterException("param action is null or empty");
			if (StringUtils.isBlank(usrId))
				throw new ParameterException("param usr Id is null or empty");

			String encryptedUsrId = new String(Base64.getDecoder().decode(usrId));

			ClickargoUser opEntity = clickargoUserService.updateUserStatus(action, encryptedUsrId);

			// PORTEDI-1921 to force logout the user.
//			Optional<CoreUsr> opCoreUsr = Optional.of(opEntity.getCoreUsr());
//			if (opCoreUsr.isPresent() && opCoreUsr.get().getUsrStatus() == RecordStatus.INACTIVE.getCode()
//					&& action.equalsIgnoreCase(UserAction.DEACTIVATE.getAction())) {
//				LogoutEvent event = new LogoutEvent(this, encryptedUsrId, Action.FORCE_LOGOUT);
//				eventPublisher.publishEvent(event);
//			}

			return ResponseEntity.ok(objectMapper.writeValueAsString(opEntity));

		} catch (PermissionException ex) {
			log.error("updateUserStatus", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("updateUserStatus", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{action}/{usrId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUser(@PathVariable String action, @PathVariable String usrId,
			@RequestBody ClickargoUser portalUser) {
		log.debug("updateUser");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			if (StringUtils.isBlank(action))
				throw new ParameterException("param action is null or empty");
			if (StringUtils.isBlank(usrId))
				throw new ParameterException("param usrId is null or empty");

			String encryptedUsrId = new String(Base64.getDecoder().decode(usrId));

//			boolean isRolesChanged = false;
//			if (action.equals("save")) {
//				CkUser dbPortalUser = ckUserService.getUser(encryptedUsrId);
//				if (dbPortalUser != null && dbPortalUser.getHoldRoleList() != null
//						&& portalUser.getHoldRoleList() != null
//						&& dbPortalUser.getHoldRoleList().size() != portalUser.getHoldRoleList().size()) {
//					isRolesChanged = true;
//				}
//			}

			ClickargoUser opEntity = clickargoUserService.updateUserByAction(action, encryptedUsrId, portalUser);
			// To force logout the user.
//			Optional<CoreUsr> opCoreUsr = Optional.of(opEntity.getCoreUsr());
//			if ((opCoreUsr.isPresent() && opCoreUsr.get().getUsrStatus() == RecordStatus.INACTIVE.getCode()
//					&& action.equalsIgnoreCase(UserAction.DEACTIVATE.getAction())) || isRolesChanged) {
//
//				LogoutEvent event = new LogoutEvent(this, encryptedUsrId, Action.FORCE_LOGOUT);
//				eventPublisher.publishEvent(event);
//
//			}

			return ResponseEntity.ok(objectMapper.writeValueAsString(opEntity));

		} catch (PermissionException ex) {
			log.error("updateUser", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("updateUser", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntitiesBy(@RequestParam Map<String, String> params) {
		log.debug("getEntitiesBy");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			Optional<Object> opEntity = clickargoUserService.getEntitiesByProxy(params);
			return ResponseEntity.ok(opEntity.get());
//		} catch (PermissionException ex) {
//			log.error("getEntitiesBy", ex);
//			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
//			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
//			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
//		} catch (PathNotFoundException ex) {
//			log.error("getEntityById", ex);
//			serviceStatus.setStatus(STATUS.EXCEPTION);
//			serviceStatus.setErr(new ServiceError(-500, ex.getMessage()));
//			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			log.error("getEntityById", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/tCkAccn/{accnId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getTCkAccn(@PathVariable String accnId) {
		log.debug("getEntitiesBy");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			if("-".equalsIgnoreCase(accnId)) {
				accnId = super.getPrincipal().getUserAccnId();
			}

			CkAccn ckAccn = coreAccnService.getCkAccn(accnId);
			
			return ResponseEntity.ok(ckAccn);

		} catch (Exception ex) {
			log.error("getEntityById", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}

}
