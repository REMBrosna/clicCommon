package com.guudint.clickargo.journal.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;

public interface IJournalService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal reserve(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal reverse(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal utilize(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal pay(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal find(CkCreditJournal dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    List<CkCreditJournal> findByAnd(CkCreditJournal dto, int iDisplayStart, int iDisplayLength, String selectClause, String orderByClause) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    int countByAnd(CkCreditJournal dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCreditJournal add(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    List<CkCreditJournal> filterBy(EntityFilterRequest paramEntityFilterRequest) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;
}
