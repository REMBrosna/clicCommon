package com.guudint.clickargo.credit.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditDaoImpl extends GenericDaoImpl<TCkCredit, String> implements CkCreditDao {

    @Override
    public TCkCredit getByServiceTypeAndAccnAndCcy(CkMstServiceType serviceType, CoreAccn coreAccn, MstCurrency ccy) {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(TCkCredit.class);
            criteria.add(Restrictions.eq("TCkMstServiceType.svctId", serviceType.getSvctId()));
            criteria.add(Restrictions.eq("TCoreAccn.accnId", coreAccn.getAccnId()));
            criteria.add(Restrictions.eq("TMstCurrency.ccyCode", ccy.getCcyCode()));
            criteria.add(Restrictions.eq("crStatus", Constant.ACTIVE_STATUS));
            return getOne(criteria);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Map<String, String>> findDistinctCompanyByState(String state) throws Exception {
        String sql = "select DISTINCT tca.ACCN_ID ,tca.ACCN_NAME "
                + "from T_CK_CREDIT tcc "
                + "inner join T_CORE_ACCN tca on tca.ACCN_ID = tcc.CR_COMPANY "
                + "where tcc.CR_STATE = :state";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("state", state);
        List<Map<String, String>> companyList = new ArrayList<>();
        for (Object object : query.list()) {
            Object[] obj = (Object[]) object;
            Map<String, String> map = new HashMap<>();
            map.put("companyId", Optional.ofNullable(obj[0]).orElse("").toString());
            map.put("companyName", Optional.ofNullable(obj[1]).orElse("").toString());
            companyList.add(map);
        }
        return companyList;
    }

    @Override
    public List<String> findDistinctServiceByCompanyAndState(String companyId, String state) throws Exception {
        String sql = "select DISTINCT CR_SERVICE_TYPE "
                + " from T_CK_CREDIT tcc "
                + " where tcc.CR_COMPANY = :companyId and tcc.CR_STATE = :state";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setParameter("companyId", companyId);
        query.setParameter("state", state);
        List<String> serviceList = new ArrayList<>();
        for (Object object : query.list()) {
            serviceList.add(String.class.cast(object));
        }
        return serviceList;
    }

    @Override
    public TCkCredit findByServiceTypeAndAccn(String serviceType, String accnId) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkCredit.class);
        criteria.add(Restrictions.eq("TCkMstServiceType.svctId", serviceType));
        criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
        criteria.add(Restrictions.eq("crStatus", Constant.ACTIVE_STATUS));
        return getOne(criteria);
    }

}
