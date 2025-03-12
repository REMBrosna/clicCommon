/**
 * 
 */
package com.guudint.clickargo.master.dao;

import com.guudint.clickargo.master.model.TCkMstTaskState;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkMstTaskStateDao extends GenericDao<TCkMstTaskState, String> {

    TCkMstTaskState getByIdAndStatus(String id, Character status);

}
