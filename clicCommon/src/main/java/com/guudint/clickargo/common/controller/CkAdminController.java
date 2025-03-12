package com.guudint.clickargo.common.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guudint.clickargo.controller.AbstractCkController;

@RequestMapping(value = "/api/v1/clickargo/admin")
@CrossOrigin
public class CkAdminController extends AbstractCkController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkAttachmentsController.class);

	// Interface Methods
	/////////////////////
	@GetMapping("/{entity}/new")
	public ResponseEntity<Object> getEntityById(@PathVariable String entity) {
		log.debug("getEntityById Controller");
		return super.newEntity(entity);
	}
}
