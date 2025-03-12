/**
 * 
 */
package com.guudint.clickargo.master.dao;

import java.util.List;

import com.guudint.clickargo.master.model.TCkMstCntType;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkMstCntTypeDao extends GenericDao<TCkMstCntType, String> {

    List<TCkMstCntType> findByCnttStatus(Character status) throws Exception;
}
