package com.guudint.clickargo.credit.service;

import java.util.Date;
import java.util.List;

import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.vcc.camelone.common.exception.ProcessingException;

public interface ICreditRequestService {

    List<CkCreditRequest> getQueueToActive(Date startDate);

    void setActiveCreditLimit(CkCreditRequest ckCreditRequest) throws ProcessingException, Exception;
}
