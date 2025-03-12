/**
 * 
 */
package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.master.dao.CkMstJobStateDao;
import com.guudint.clickargo.master.model.TCkMstJobState;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author billy
 *
 */
public class CkMstJobStateDaoImpl extends GenericDaoImpl<TCkMstJobState, String> implements CkMstJobStateDao {

    @Override
    @Transactional(readOnly = true)
    public TCkMstJobState getByIdAndStatus(String id, Character status) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            paramMap.put("status", status);
            List<TCkMstJobState> tCkMstJobStateList = getByQuery("select o from TCkMstJobState o where jbstId = :id and jbstStatus = :status", paramMap);
            return tCkMstJobStateList.isEmpty() ? null : tCkMstJobStateList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
