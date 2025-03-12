/**
 * 
 */
package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.CkMstShipmentTypeDao;
import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author billy
 *
 */
public class CkMstShipmentTypeDaoImpl extends GenericDaoImpl<TCkMstShipmentType, String>
		implements CkMstShipmentTypeDao {

	@Override
	public TCkMstShipmentType getByIdAndStatus(String id, Character status) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("id", id);
			paramMap.put("status", status);
			List<TCkMstShipmentType> tCkMstShipmentTypeList = getByQuery("select o from TCkMstShipmentType o where shtId = :id and shtStatus = :status", paramMap);
			return tCkMstShipmentTypeList.isEmpty() ? null : tCkMstShipmentTypeList.get(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
