package com.guudint.clickargo.job.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.guudint.clickargo.job.dto.CkJobQuery;
import com.guudint.clickargo.job.service.impl.CkQueryService;
import com.vcc.camelone.common.controller.entity.AbstractPortalController;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.PermissionException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

@RequestMapping(value = "/api/v1/clickargo/query/job")
@CrossOrigin
@Controller
public class QueryController extends AbstractPortalController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(QueryController.class);

	@Autowired
	private CkQueryService queryService;

	@GetMapping(value = "/list/{jobId}")
	public ResponseEntity<Object> fetchQueries(@PathVariable String jobId) {

		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			// validation
			List<CkJobQuery> queryList = queryService.fetchQuery(jobId, getPrincipal());

			return ResponseEntity.ok(queryList);

		} catch (PermissionException ex) {
			log.error("fetchQueries", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("fetchQueries", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/reply/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> replyQuery(@RequestBody CkJobQuery ckQuery, @PathVariable String id) {

		log.info("replyQuery");

		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			// validation
			String replyMessage = ckQuery.getQryResponse();

			if (StringUtils.isEmpty(replyMessage)) {
				throw new Exception("Reply message is null.");
			}
			if (replyMessage.trim().length() > 1024) {
				throw new Exception("Reply message should less than 1024 characters.");
			}

			queryService.replyQuery(id, replyMessage, getPrincipal());
			return ResponseEntity.ok("");

		} catch (PermissionException ex) {
			log.error("replyQuery", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("replyQuery", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Object> sendQuery(@RequestBody CkJobQuery ckQuery) {

		log.info("replyQuery");

		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			// validation
			if(ckQuery == null)
				throw new ParameterException("param ckQuery is null");
			
			String qryMsg = ckQuery.getQryQuery();

			if (StringUtils.isEmpty(qryMsg)) {
				throw new Exception("Query message is null.");
			}
			if (qryMsg.trim().length() > 1024) {
				throw new Exception("Query message should less than 1024 characters.");
			}

			queryService.createQuery(ckQuery, getPrincipal());
			return ResponseEntity.ok("");

		} catch (PermissionException ex) {
			log.error("replyQuery", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("replyQuery", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removeQuery(@PathVariable String id) {
		log.info("removeQuery");

		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			
			queryService.removeQuery(id, getPrincipal());
			return ResponseEntity.ok("");

		} catch (PermissionException ex) {
			log.error("removeQuery", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("removeQuery", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getQuery(@PathVariable String id) {
		log.info("getQuery");

		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			CkJobQuery query = queryService.getQuery(id, getPrincipal());
			return ResponseEntity.ok(query);
		} catch (PermissionException ex) {
			log.error("getQuery", ex);
			serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
			serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
		} catch (Exception ex) {
			log.error("getQuery", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
}
