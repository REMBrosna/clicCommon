package com.guudint.clickargo.master.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.master.dao.CoreAccnDao;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Primary
@Repository
public class CoreAccnDaoImpl extends GenericDaoImpl<TCoreAccn, String> implements CoreAccnDao {

    @Override
    public TCoreAccn getByAccnIdAndStatus(String accnId, Character status) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TCoreAccn.class);
            dc.add(Restrictions.eq("accnId", accnId));
            dc.add(Restrictions.eq("accnStatus", status));
            return getOne(dc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<TCoreAccn> getByAccnTypeAndStatus(AccountTypes accnType, Character status) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("accnType", accnType.name());
            paramMap.put("status", status);
            return getByQuery("select o from TCoreAccn o where o.TMstAccnType.atypId = :accnType and o.accnStatus = :status", paramMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<TCoreAccn> fetchAccnWithoutInAccnConfig(List<String> accnTypeList, String accnConfigKey) throws Exception {
    	
		String hql = "from TCoreAccn accn " 
				+ "		where accn.TMstAccnType.atypId in :accnTypeList "
				+ " 		and accn.accnId not in ("
				+ "				select ac.id.acfgAccnid from TCoreAccnConfig ac where ac.id.acfgKey = :acfgKey) ";

		Map<String, Object> params = new HashMap<>();
		params.put("accnTypeList", accnTypeList);
		params.put("acfgKey", accnConfigKey);

		return getByQuery(hql, params);
	}

	@Override
	public TCoreAccn findAccnByUen(String uen) throws Exception {

		DetachedCriteria dc = DetachedCriteria.forClass(TCoreAccn.class);
		dc.add(Restrictions.eq("accnCoyRegn", uen));
		return getOne(dc);

	}

	@Override
	public List<TCoreAccn> findAllByUen(String uen) throws Exception {

		DetachedCriteria dc = DetachedCriteria.forClass(TCoreAccn.class);
		dc.add(Restrictions.eq("accnCoyRegn", uen));
		return super.getByCriteria(dc);

	}
}
