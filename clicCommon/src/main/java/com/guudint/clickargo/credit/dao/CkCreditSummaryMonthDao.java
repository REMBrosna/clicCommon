package com.guudint.clickargo.credit.dao;

import com.guudint.clickargo.credit.model.TCkCreditSummaryMonth;
import com.guudint.clickargo.credit.model.TCkCreditSummaryMonthId;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.master.dto.MstCurrency;

public interface CkCreditSummaryMonthDao extends GenericDao<TCkCreditSummaryMonth, TCkCreditSummaryMonthId> {
    
    TCkCreditSummaryMonth getByServiceTypeAndAccnAndCcyAndDate(CkMstServiceType serviceType, CoreAccn coreAccn, MstCurrency ccy, String date);
}
