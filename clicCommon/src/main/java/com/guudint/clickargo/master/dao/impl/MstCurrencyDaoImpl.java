package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.MstCurrencyDao;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.master.model.TMstCurrency;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

public class MstCurrencyDaoImpl extends GenericDaoImpl<TMstCurrency, String> implements MstCurrencyDao {

    @Override
    @Transactional(readOnly = true)
    public TMstCurrency getByCodeAndStatus(String ccyCode, Character status) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TMstCurrency.class);
            dc.add(Restrictions.eq("ccyCode", ccyCode));
            dc.add(Restrictions.eq("ccyStatus", status));
            return getOne(dc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
