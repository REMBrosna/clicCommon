package com.guudint.clickargo.common.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guudint.clickargo.common.service.ICkCompGuide;
import com.vcc.camelone.common.controller.entity.AbstractEntityController;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;

@CrossOrigin
@RestController("baseComponentGuideController")
@RequestMapping(value = "/api/v1/clickargo/component-guide")
public class ComponentGuideController extends AbstractEntityController {

    private static final Logger LOG = Logger.getLogger(ComponentGuideController.class);

    @Autowired
    ICkCompGuide compGuide;

    @GetMapping("/{componentId}/")
    public ResponseEntity<Object> getComponentGuide(@PathVariable String componentId) {
        LOG.debug("getComponentGuide");
        try {
            return ResponseEntity.ok(compGuide.getCompGuides(componentId, getPrincipal()));
        } catch (Exception e) {
            LOG.error("getComponentGuide", e);
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, e));
            return ResponseEntity.badRequest().body(serviceStatus);
        }
    }

}
