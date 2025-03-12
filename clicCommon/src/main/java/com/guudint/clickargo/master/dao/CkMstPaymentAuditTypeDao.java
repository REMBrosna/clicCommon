/**
 *
 */
package com.guudint.clickargo.master.dao;

import com.guudint.clickargo.master.model.TCkMstPaymentAuditType;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 */
public interface CkMstPaymentAuditTypeDao extends GenericDao<TCkMstPaymentAuditType, String> {

    TCkMstPaymentAuditType findByIdAndStatus(String id, char status);

}
