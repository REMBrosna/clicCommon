/**
 *
 */
package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.CkMstTaskTypeDao;
import com.guudint.clickargo.master.model.TCkMstTaskType;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author billy
 *
 */
public class CkMstTaskTypeDaoImpl extends GenericDaoImpl<TCkMstTaskType, String> implements CkMstTaskTypeDao {

    @Override
    public TCkMstTaskType getByIdAndStatus(String id, Character status) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            paramMap.put("status", status);
            List<TCkMstTaskType> tCkMstTaskTypeList = getByQuery("select o from TCkMstTaskType o where tsktId = :id and tsktStatus = :status", paramMap);
            return tCkMstTaskTypeList.isEmpty() ? null : tCkMstTaskTypeList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
