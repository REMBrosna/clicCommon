/**
 * 
 */
package com.guudint.clickargo.clicservice.dao;

import java.util.List;

import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkSvcSubDao extends GenericDao<TCkSvcSub, String> {
	
	public List<TCkSvcSub> findSubscribedAppSvc(String accnId) throws Exception ;
}
