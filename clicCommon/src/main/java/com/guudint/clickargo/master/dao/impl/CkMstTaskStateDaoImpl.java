/**
 *
 */
package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.CkMstTaskStateDao;
import com.guudint.clickargo.master.model.TCkMstTaskState;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author billy
 *
 */
public class CkMstTaskStateDaoImpl extends GenericDaoImpl<TCkMstTaskState, String> implements CkMstTaskStateDao {

    @Override
    public TCkMstTaskState getByIdAndStatus(String id, Character status) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            paramMap.put("status", status);
            List<TCkMstTaskState> tCkMstTaskStateList = getByQuery("select o from TCkMstTaskState o where tskstId = :id and tskstStatus = :status", paramMap);
            return tCkMstTaskStateList.isEmpty() ? null : tCkMstTaskStateList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}