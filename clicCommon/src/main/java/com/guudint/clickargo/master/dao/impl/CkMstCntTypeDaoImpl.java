/**
 * 
 */
package com.guudint.clickargo.master.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.master.dao.CkMstCntTypeDao;
import com.guudint.clickargo.master.model.TCkMstCntType;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

/**
 * @author billy
 *
 */
public class CkMstCntTypeDaoImpl extends GenericDaoImpl<TCkMstCntType, String> implements CkMstCntTypeDao {

    @Override
    public List<TCkMstCntType> findByCnttStatus(Character status) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkMstCntType.class);
        criteria.add(Restrictions.eq("cnttStatus", status));
        return getByCriteria(criteria);
    }

}
