/**
 *
 */
package com.guudint.clickargo.master.dao;

import com.guudint.clickargo.master.model.TCkMstPaymentType;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 */
public interface CkMstPaymentTypeDao extends GenericDao<TCkMstPaymentType, String> {

    TCkMstPaymentType getByIdAndStatus(String id, Character status);

}
