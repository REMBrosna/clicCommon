/**
 *
 */
package com.guudint.clickargo.master.dao;

import java.util.List;

import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 */
public interface CkMstServiceTypeDao extends GenericDao<TCkMstServiceType, String> {

    TCkMstServiceType getByIdAndStatus(String id, Character status);
    
    List<TCkMstServiceType> getAllActive();

}
