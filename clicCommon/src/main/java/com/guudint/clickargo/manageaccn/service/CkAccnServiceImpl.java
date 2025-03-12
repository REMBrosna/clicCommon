package com.guudint.clickargo.manageaccn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.manageaccn.dao.CkCoreAccnDao;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.service.impl.AccnService;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;

public class CkAccnServiceImpl extends AccnService {

    @Autowired
    private CkCoreAccnDao ckCoreAccnDao;

    private static Logger LOG = Logger.getLogger(CkAccnServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Object addObj(Object object, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        if (object == null) {
            throw new ParameterException("param dto null");
        }
        if (principal == null) {
            throw new ParameterException("param principal null");
        }
        CoreAccn coreAccn = (CoreAccn) object;
        CoreContact coreContact = coreAccn.getAccnContact();
        List<ValidationError> validationErrors = new ArrayList<>();
        if (coreContact == null) {
            validationErrors.add(new ValidationError("", "accnContact.contactEmail", "Email cannot be empty"));
            throw new ValidationException(validationErrorMap(validationErrors));
        }
        try {
            Optional<TCoreAccn> coreAccnOpt = ckCoreAccnDao.findByEmail(coreAccn.getAccnContact().getContactEmail());
            validationErrors.clear();
            if (coreAccnOpt.isPresent()) {
                validationErrors.add(new ValidationError("", "accnContact.contactEmail", "Email already exists"));
                throw new ValidationException(validationErrorMap(validationErrors));
            }
            return super.add(coreAccn, principal);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            if (validationErrors.isEmpty()) {
                throw new ProcessingException(e);
            } else {
                throw new ValidationException(validationErrorMap(validationErrors));
            }
        }
    }

    private String validationErrorMap(List<ValidationError> validationErrors)
            throws ParameterException, ProcessingException {
        LOG.debug("validationMap");
        if (null == validationErrors) {
            throw new ParameterException("param errros null");
        } else if (validationErrors.isEmpty()) {
            throw new ProcessingException("param errros empty");
        } else {
            try {
                Map<String, String> validationMap = new HashMap<>();
                validationErrors.stream().forEach((ve) -> {
                    LOG.error(ve.getErrorType() + " " + ve.getErrorDescription() + "  " + ve);
                    validationMap.put(ve.getErrorType().toString(), ve.getErrorDescription());
                });
                String json = (new ObjectMapper()).writeValueAsString(validationMap);
                return json;
            } catch (Exception var4) {
                LOG.error("errorMap", var4);
                throw new ProcessingException(var4.getMessage());
            }
        }
    }

}
