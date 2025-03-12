package com.guudint.clickargo.credit.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.controller.AbstractCkController;
import com.guudint.clickargo.controller.CustomSerializerProvider;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/clickargo/creditRequest")
public class CreditRequestController extends AbstractCkController{

    private static Logger LOG = Logger.getLogger(CreditRequestController.class);
    
    @PostConstruct
	public void configureObjectMapper() {
		objectMapper.setSerializerProvider(new CustomSerializerProvider());
	}

    @PostMapping("/{entity}")
	public ResponseEntity<Object> createEntity(@PathVariable String entity, @RequestBody String requestBody) {
		LOG.debug("createEntity Controller");
		return super.createEntity(entity, requestBody);
	}

	@GetMapping("/{entity}/{id}")
	public ResponseEntity<Object> getEntityById(@PathVariable String entity, @PathVariable String id) {
		LOG.debug("getEntityById Controller");
		if (StringUtils.isNotBlank(id) && StringUtils.equalsIgnoreCase(ICkConstant.DASH, id)) {
			return super.newEntity(entity);
		} else {
			return super.getEntityById(entity, id);
		}
	}

	@PutMapping("/{entity}/{id}")
	public ResponseEntity<Object> updateEntity(@RequestBody String object, @PathVariable String entity,
			@PathVariable String id) {
		LOG.debug("updateEntity Controller");
		return super.updateEntity(object, entity, id);
	}

	@DeleteMapping("/{entity}/{id}")
	public ResponseEntity<Object> deleteEntityById(@PathVariable String entity, @PathVariable String id) {
		LOG.debug("deleteEntityById Controller");
		return super.deleteEntityById(entity, id);
	}

	@GetMapping("/{entity}/list")
	public ResponseEntity<Object> getEntitiesBy(@PathVariable String entity, @RequestParam Map<String, String> params) {
		LOG.debug("getEntitiesBy Controller");
		return super.getEntitiesBy(entity, params);
	}
}
