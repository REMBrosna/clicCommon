package com.guudint.clickargo.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.guudint.clickargo.common.IClickargoEntityService;
import com.vcc.camelone.common.controller.entity.AbstractEntityController;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.PermissionException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;
import com.vcc.camelone.master.controller.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class AbstractCkController extends AbstractEntityController {

    // Static Attributes
    ////////////////////
    private static Logger log = Logger.getLogger(AbstractCkController.class);

    @PostConstruct
    public void configureObjectMapper() {
        objectMapper.setSerializerProvider(new CustomSerializerProvider());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Instantiates the object.
     */
    /**
     * @param entity
     * @return
     */
    protected ResponseEntity<Object> newEntity(@PathVariable String entity) {
        log.debug("newEntity");

        ServiceStatus serviceStatus = new ServiceStatus();
        try {

            if (StringUtils.isEmpty(entity))
                throw new ParameterException("param appType null or empty");
            Optional<Object> opDto = this.initEntity(entity);

            if (!opDto.isPresent()) {
                serviceStatus.setData(null);
                throw new Exception("entity null or empty");
            }

            return ResponseEntity.ok(objectMapper.writeValueAsString(opDto.get()).getBytes(StandardCharsets.UTF_8));

        } catch (PathNotFoundException ex) {
            log.error("newEntity", ex);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-500, ex.getMessage()));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_GATEWAY);
        } catch (PermissionException ex) {
            log.error("newEntity", ex);
            serviceStatus.setStatus(STATUS.PERMISSION_FAILED);
            serviceStatus.setErr(new ServiceError(403, ex.getMessage()));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            log.error("newEntity", ex);
            serviceStatus.setStatus(STATUS.EXCEPTION);
            serviceStatus.setErr(new ServiceError(-100, ex.getMessage()));
            return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
        }
    }

    // Helper Methods
    /////////////////////////

    /**
     * @param entity
     * @return
     * @throws ParameterException
     * @throws PathNotFoundException
     * @throws ProcessingException
     */
	protected Optional<Object> initEntity(String entity)
            throws ParameterException, PathNotFoundException, ProcessingException {

        log.debug("initApp");
        try {
            if (StringUtils.isEmpty(entity))
                throw new ParameterException("param appType null or empty");
            if (!entityDTOs.containsKey(entity))
                throw new PathNotFoundException("appType not mapped: " + entity);
            if (!entityServices.containsKey(entity))
                throw new PathNotFoundException("appType not mapped: " + entity);

            Class<?> formClass = Class.forName(entityDTOs.get(entity));
            AbstractDTO<?, ?> entityDto = (AbstractDTO<?, ?>) formClass.newInstance();

            Object bean = applicationContext.getBean(entityServices.get(entity));
            IClickargoEntityService<?, ?, ?> service = (IClickargoEntityService<?, ?, ?>) bean;
            entityDto = (AbstractDTO<?, ?>) service.newObj(getPrincipal());
            return Optional.of(entityDto);

        } catch (ParameterException | PathNotFoundException ex) {
            log.error("initApp", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("initApp", ex);
            throw new ProcessingException(ex);
        }
    }
}
