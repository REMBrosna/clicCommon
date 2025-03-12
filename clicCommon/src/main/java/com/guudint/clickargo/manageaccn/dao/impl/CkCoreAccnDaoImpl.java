package com.guudint.clickargo.manageaccn.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.manageaccn.dao.CkCoreAccnDao;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkCoreAccnDaoImpl extends GenericDaoImpl<TCoreAccn, String> implements CkCoreAccnDao {

    @Override
    public Optional<TCoreAccn> findByEmail(String email) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCoreAccn.class);
        criteria.add(Restrictions.eq("accnContact.contactEmail", email));
        return Optional.ofNullable(getOne(criteria));
    }


    @Override
    public List<TCoreAccn> findByAccnName(String accnName) throws Exception {
    	
		//String hql = "from TCoreAccn o " + "where replace(o.accnName, ' ','') = :accnName";
		String hql = "from TCoreAccn o " + "where o.accnName = :accnName";
		Map<String, Object> params = new HashMap<>();
		params.put("accnName", accnName);
		return getByQuery(hql, params);
    }
}
