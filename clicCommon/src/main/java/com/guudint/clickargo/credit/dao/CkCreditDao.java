package com.guudint.clickargo.credit.dao;

import java.util.List;
import java.util.Map;

import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.master.dto.MstCurrency;

public interface CkCreditDao extends GenericDao<TCkCredit, String> {
    
    TCkCredit getByServiceTypeAndAccnAndCcy(CkMstServiceType serviceType, CoreAccn coreAccn, MstCurrency ccy);

    List<Map<String, String>> findDistinctCompanyByState(String state) throws Exception;

    List<String> findDistinctServiceByCompanyAndState(String companyId, String state) throws Exception;

    TCkCredit findByServiceTypeAndAccn(String serviceType, String accnId) throws Exception;
}
