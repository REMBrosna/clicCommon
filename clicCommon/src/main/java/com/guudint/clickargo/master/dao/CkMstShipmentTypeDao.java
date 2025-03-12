/**
 * 
 */
package com.guudint.clickargo.master.dao;

import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkMstShipmentTypeDao extends GenericDao<TCkMstShipmentType, String> {

    TCkMstShipmentType getByIdAndStatus(String id, Character status);

}
