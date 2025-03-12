/**
 * 
 */
package com.guudint.clickargo.master.dao;

import com.guudint.clickargo.master.model.TCkMstTaskType;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkMstTaskTypeDao extends GenericDao<TCkMstTaskType, String> {

    TCkMstTaskType getByIdAndStatus(String id, Character status);

}
