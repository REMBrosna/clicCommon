package com.guudint.clickargo.credit.dao;

import java.util.List;

import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkCreditRequestDao extends GenericDao<TCkCreditRequest, String> {


    List<TCkCreditRequest> findByStateAndStartDt(String state, String dateStart) throws Exception;
   
    List<TCkCreditRequest> findByServiceTypeAndAccnId(String serviceType, String accnId) throws Exception;
}
