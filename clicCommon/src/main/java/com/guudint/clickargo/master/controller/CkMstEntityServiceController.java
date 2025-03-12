package com.guudint.clickargo.master.controller;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

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

import com.vcc.camelone.common.controller.entity.AbstractEntityController;
import com.vcc.camelone.common.service.PortalCacheTimeStampService;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.event.PortalCacheTimeStampEvent;

@RequestMapping(value = "/api/v1/clickargo/master")
@CrossOrigin
@RestController
public class CkMstEntityServiceController extends AbstractEntityController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkMstEntityServiceController.class);

	private static final String MODULE_NAME = "master";

	@Autowired
	PortalCacheTimeStampService portalCacheTimeStampService;

	// Interface Methods
	/////////////////////
	/**
	 * (non-Javadoc)
	 *
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#createEntity(java.lang.String,
	 *      java.lang.String)
	 * @return
	 */
	@RequestMapping(value = "/{entity}", method = RequestMethod.POST)
	public ResponseEntity<Object> createEntity(@PathVariable String entity, @RequestBody String object) {
		log.debug("createEntity");

		applicationContext.publishEvent(new PortalCacheTimeStampEvent(this, MODULE_NAME, entity));

		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			return super.createEntity(entity, object);
		} catch (Exception e) {
			log.error("createEntity", e);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntities(java.lang.String)
	 */
	@RequestMapping(value = "/{entity}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntities(@PathVariable String entity) {
		log.debug("getEntities");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			return super.getEntities(entity);
		} catch (Exception e) {
			log.error("getEntities", e);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntityById(java.lang.String,
	 *      java.lang.String)
	 */
	@RequestMapping(value = "{entity}/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntityById(@PathVariable String entity, @PathVariable String id) {
		log.debug("getEntityById");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			return super.getEntityById(entity, id);
		} catch (Exception e) {
			log.error("getEntityById", e);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#updateEntity(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = "{entity}/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateEntity(@RequestBody String object, @PathVariable String entity,
			@PathVariable String id) {
		log.info("updateEntity");
		ServiceStatus serviceStatus = new ServiceStatus();

		try {
			applicationContext.publishEvent(new PortalCacheTimeStampEvent(this, MODULE_NAME, entity));
			return super.updateEntity(object, entity, id);
		} catch (Exception e) {
			log.error("updateEntity", e);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = { "{entity}/{id}/{action}" }, method = { RequestMethod.PUT })
	public ResponseEntity<Object> updateEntityStatus(@RequestBody String object, @PathVariable String entity,
			@PathVariable String id, @PathVariable String action) {
		log.info("updateEntityStatus");
		this.applicationContext.publishEvent(new PortalCacheTimeStampEvent(this, "master", entity));
		return super.updateEntityStatus(object, entity, id, action);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#deleteEntityById(java.lang.String,
	 *      java.lang.String)
	 */
	@RequestMapping(value = "{entity}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteEntityById(@PathVariable String entity, @PathVariable String id) {
		log.debug("getEntityById");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			applicationContext.publishEvent(new PortalCacheTimeStampEvent(this, MODULE_NAME, entity));

			return super.deleteEntityById(entity, id);
		} catch (Exception e) {
			log.error("updateEntity", e);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntitiesBy(java.lang.String,
	 *      java.util.Map)
	 */
	@RequestMapping(value = "/{entity}/list", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntitiesBy(@PathVariable String entity, @RequestParam Map<String, String> params) {
		log.debug("getEntitiesBy");
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			return super.getEntitiesBy(entity, params);
		} catch (Exception e) {
			log.error("updateEntity", e);
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);

		}
	}

	@PostConstruct
	public void postConstruct() {
		this.initMasterData();
	}

	private void initMasterData() {

		Map<String, Date> serviceMap = portalCacheTimeStampService.getLastUpdatedtimeStamp().get(MODULE_NAME);
		Date now = new Date();

		Set<String> keySet = this.getEntityServices().keySet();
		keySet.forEach(key -> serviceMap.put(key, now));
	}

}
