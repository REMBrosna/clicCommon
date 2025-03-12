/**
 * 
 */
package com.guudint.clickargo.clicservice.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.clicservice.dao.CkSvcSubDao;
import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

/**
 * @author billy
 *
 */
public class CkSvcSubDaoImpl extends GenericDaoImpl<TCkSvcSub, String> implements CkSvcSubDao {

    @Override
    public List<TCkSvcSub> findSubscribedAppSvc(String accnId) throws Exception {
    	
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkSvcSub.class);
        
        criteria.add(Restrictions.eq("TCkMstSvcSubState.ssstId", "APR"));
        criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
        
        return super.getByCriteria(criteria);
    }
}
