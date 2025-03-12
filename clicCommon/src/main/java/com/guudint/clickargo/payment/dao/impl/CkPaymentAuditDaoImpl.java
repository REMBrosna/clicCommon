/**
 * 
 */
package com.guudint.clickargo.payment.dao.impl;

import com.guudint.clickargo.payment.dao.CkPaymentAuditDao;
import com.guudint.clickargo.payment.model.TCkPaymentAudit;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author billy
 *
 */
public class CkPaymentAuditDaoImpl extends GenericDaoImpl<TCkPaymentAudit, String> implements CkPaymentAuditDao {

    @Override
    @Transactional(readOnly = true)
    public List<TCkPaymentAudit> getByReferenceAndStatus(String reference, char status) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TCkPaymentAudit.class);
            dc.add(Restrictions.eq("pyaReference", reference));
            dc.add(Restrictions.eq("pyaStatus", status));
            return getByCriteria(dc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
