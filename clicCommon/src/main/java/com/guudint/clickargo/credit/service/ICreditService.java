package com.guudint.clickargo.credit.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.credit.dto.CkCredit;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;

public interface ICreditService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit createCredit(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit verifyCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit approveCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit rejectCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit suspendCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit unsuspendCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit find(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    CkCredit fetch(CkCredit dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCredit findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    List<CkCredit> findByAnd(CkCredit dto, int iDisplayStart, int iDisplayLength, String selectClause, String orderByClause) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    int countByAnd(CkCredit dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    CkCredit add(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    List<CkCredit> filterBy(EntityFilterRequest paramEntityFilterRequest) throws ParameterException, EntityNotFoundException, ProcessingException, Exception;
    
    List<Map<String, String>> findDistinctCompany();

    List<String> findDistinctServiceByCompany(String companyId);

    CkCredit findByServiceTypeAndAccnId(String serviceType, String accnId) throws ParameterException;

    CkCredit update(CkCredit dto)throws ParameterException, ProcessingException, Exception;
}








/* public interface ICredit {
    
    public CkCredit createCredit(CreateCredit request) throws ParameterException, ProcessingException, Exception; 

    public CkCredit verifyCredit(VerifyCredit request) throws ParameterException, ProcessingException, Exception; 

    public CkCredit approveCredit(ApproveCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCredit rejectCredit(RejectCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCredit suspendCredit(SuspendCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCredit unsuspendCredit(UnsuspendCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCreditJournal reserveCredit(ReserveCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCreditJournal utilizeCredit(UtilizeCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCreditJournal reverseCredit(ReverseCredit request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCreditSummary getCreditBalance(CreditRequest request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCredit getCredit(CreditRequest request) throws ParameterException, ProcessingException, Exception; 
    
    public CkCreditJournal payCredit(PaymentCredit request) throws ParameterException, ProcessingException, Exception; 

    public Map<String, Object> listOfJournal(String serviceType, String accn, String ccy) throws ParameterException, ProcessingException, Exception; 
} */
