package com.guudint.clickargo.manageaccn.controller;

import java.util.Map;

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

import com.vcc.camelone.common.controller.entity.AbstractEntityController;

@Deprecated
@CrossOrigin
@RequestMapping("/api/v1/clickargo/accn")
public class CkAccnController extends AbstractEntityController {

    private static final String entity = "ckAccn";
    private static Logger LOG = Logger.getLogger(CkAccnController.class);

    @PostMapping
    public ResponseEntity<Object> createEntity(@RequestBody String object) {
        LOG.debug("createEntity");
        return super.createEntity(entity, object);
    }

    @GetMapping
    public ResponseEntity<Object> getEntities() {
        LOG.debug("getEntities");
        return super.getEntities(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEntityById(@PathVariable String id) {
        LOG.debug("getEntityById");
        return super.getEntityById(entity, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEntity(@RequestBody String object, @PathVariable String id) {
        LOG.debug("updateEntity");
        return super.updateEntity(object, entity, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEntityById(@PathVariable String id) {
        LOG.debug("getEntityById");
        return super.deleteEntityById(entity, id);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getEntitiesBy(@RequestParam Map<String, String> params) {
        LOG.debug("getEntitiesBy");
        return super.getEntitiesBy(entity, params);
    }
}
