package com.guudint.clickargo.clicservice.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.guudint.clickargo.clicservice.service.impl.CkSvcAuthService;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.controller.entity.AbstractEntityController;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

@RequestMapping(value = "/api/v1/clickargo/clicSvc")
@CrossOrigin
public class CkSvcEntityServiceController extends AbstractEntityController {

	// Static Attributes
		////////////////////
		private static Logger log = Logger.getLogger(CkSvcEntityServiceController.class);
		
		@Autowired
		private CkSvcAuthService ckSvcAuthService;
		
		// Interface Methods
		/////////////////////
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#createEntity(java.lang.String,
		 *      java.lang.String)
		 */
		@RequestMapping(value = "/{entity}", method = RequestMethod.POST)
		public ResponseEntity<Object> createEntity(@PathVariable String entity, @RequestBody String object) {
			log.debug("createEntity");
			return super.createEntity(entity, object);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntities(java.lang.String)
		 */
		@RequestMapping(value = "/{entity}", method = RequestMethod.GET)
		public ResponseEntity<Object> getEntities(@PathVariable String entity) {
			log.debug("getEntities");
			return super.getEntities(entity);
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
			return super.getEntityById(entity, id);
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
			log.debug("updateEntity");
			return super.updateEntity(object, entity, id);
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
			return super.deleteEntityById(entity, id);
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
			return super.getEntitiesBy(entity, params);
		}
		
		/**
		 * @param isAuthorized
		 * @return
		 */
		@GetMapping(value = "/{entity}/authParties/{isAuthorized}/{accnId}")
		public ResponseEntity<Object> authorizedParties(@PathVariable String isAuthorized, @PathVariable String accnId) {
			ServiceStatus serviceStatus = new ServiceStatus();
			try {

				List<CoreAccn> authorizedParties = ckSvcAuthService.getAuthorizedParties(isAuthorized, accnId);

				ObjectMapper objMapper = new ObjectMapper();
				objMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				objMapper.setSerializationInclusion(Include.NON_NULL);
				return ResponseEntity.ok(objMapper.writeValueAsString(authorizedParties));
			} catch (Exception ex) {
				log.error("authorizedParties", ex);
				serviceStatus.setStatus(STATUS.EXCEPTION);
				serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
				return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
			}
		}
		
		/**
		 * @param accnId
		 * @return
		 */
		@GetMapping(value = "/{entity}/authParty/{accnId}/{authParty}")
		public ResponseEntity<Object> isAuthorized(@PathVariable String accnId, @PathVariable String authParty) {
			ServiceStatus serviceStatus = new ServiceStatus();
			try {

				boolean authorized = ckSvcAuthService.isAuthorized(accnId, authParty);

				ObjectMapper objMapper = new ObjectMapper();
				objMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				objMapper.setSerializationInclusion(Include.NON_NULL);
				return ResponseEntity.ok(objMapper.writeValueAsString(authorized));
			} catch (Exception ex) {
				log.error("isAuthorized", ex);
				serviceStatus.setStatus(STATUS.EXCEPTION);
				serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
				return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
			}
		}

}
