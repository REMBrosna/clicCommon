package com.guudint.clickargo.credit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.credit.dao.CkCreditSummaryMonthDao;
import com.guudint.clickargo.credit.model.TCkCreditSummaryMonth;
import com.guudint.clickargo.credit.model.TCkCreditSummaryMonthId;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditSummaryMonthDaoImpl extends GenericDaoImpl<TCkCreditSummaryMonth, TCkCreditSummaryMonthId> implements CkCreditSummaryMonthDao {

    @Override
    public TCkCreditSummaryMonth getByServiceTypeAndAccnAndCcyAndDate(CkMstServiceType serviceType, CoreAccn coreAccn, MstCurrency ccy, String date) {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(TCkCreditSummaryMonth.class);
            criteria.add(Restrictions.eq("TCkMstServiceType.svctId", serviceType.getSvctId()));
            criteria.add(Restrictions.eq("TCoreAccn.accnId", coreAccn.getAccnId()));
            criteria.add(Restrictions.eq("TMstCurrency.ccyCode", ccy.getCcyCode()));
            criteria.add(Restrictions.eq("id.crsmMonth", date));
            criteria.add(Restrictions.eq("crsmStatus", Constant.ACTIVE_STATUS));
            return getOne(criteria);
        } catch (Exception e) {
            return null;
        }
    }
    
}
