package com.guudint.clickargo.credit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.credit.dao.CkCreditSummaryDao;
import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditSummaryDaoImpl extends GenericDaoImpl<TCkCreditSummary, String> implements CkCreditSummaryDao {

    @Override
    public TCkCreditSummary getByServiceTypeAndAccnAndCcy(CkMstServiceType serviceType, CoreAccn coreAccn, MstCurrency ccy) {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(TCkCreditSummary.class);
            criteria.add(Restrictions.eq("TCkMstServiceType.svctId", serviceType.getSvctId()));
            criteria.add(Restrictions.eq("TCoreAccn.accnId", coreAccn.getAccnId()));
            criteria.add(Restrictions.eq("TMstCurrency.ccyCode", ccy.getCcyCode()));
            criteria.add(Restrictions.eq("crsStatus", Constant.ACTIVE_STATUS));
            return getOne(criteria);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
