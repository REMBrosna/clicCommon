package com.guudint.clickargo.common.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcc.camelone.common.controller.entity.AbstractEntityController;

@RequestMapping(value = "/api/v1/clickargo/payment")
@CrossOrigin
public class CkPaymentController extends AbstractEntityController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkPaymentController.class);

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
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntitiesBy(java.lang.String,
	 *      java.util.Map)
	 */
	@RequestMapping(value = "/{entity}/list", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntitiesBy(@PathVariable String entity, @RequestParam Map<String, String> params) {
		log.debug("getEntitiesBy");
		return super.getEntitiesBy(entity, params);
	}
}
