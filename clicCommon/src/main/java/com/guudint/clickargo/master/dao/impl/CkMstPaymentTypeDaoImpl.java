/**
 *
 */
package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.CkMstPaymentTypeDao;
import com.guudint.clickargo.master.model.TCkMstPaymentType;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

public class CkMstPaymentTypeDaoImpl extends GenericDaoImpl<TCkMstPaymentType, String> implements CkMstPaymentTypeDao {

    @Override
    @Transactional(readOnly = true)
    public TCkMstPaymentType getByIdAndStatus(String id, Character status) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TCkMstPaymentType.class);
            dc.add(Restrictions.eq("ptyId", id));
            dc.add(Restrictions.eq("pytStatus", status));
            return getOne(dc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
