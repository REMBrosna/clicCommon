/**
 * 
 */
package com.guudint.clickargo.payment.dao;

import com.guudint.clickargo.payment.model.TCkPaymentAudit;
import com.vcc.camelone.common.dao.GenericDao;

import java.util.List;

/**
 * @author billy
 *
 */
public interface CkPaymentAuditDao extends GenericDao<TCkPaymentAudit, String> {

    List<TCkPaymentAudit> getByReferenceAndStatus(String reference, char status);

}
