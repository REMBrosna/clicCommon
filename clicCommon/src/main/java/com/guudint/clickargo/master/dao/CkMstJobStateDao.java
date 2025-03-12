/**
 * 
 */
package com.guudint.clickargo.master.dao;

import com.guudint.clickargo.master.model.TCkMstJobState;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkMstJobStateDao extends GenericDao<TCkMstJobState, String> {

    TCkMstJobState getByIdAndStatus(String id, Character status);

}
