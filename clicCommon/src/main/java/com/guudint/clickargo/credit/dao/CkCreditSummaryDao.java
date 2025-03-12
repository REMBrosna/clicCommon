package com.guudint.clickargo.credit.dao;

import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.master.dto.MstCurrency;

public interface CkCreditSummaryDao extends GenericDao<TCkCreditSummary, String> {
    
    TCkCreditSummary getByServiceTypeAndAccnAndCcy(CkMstServiceType serviceType, CoreAccn coreAccn, MstCurrency ccy);
}
