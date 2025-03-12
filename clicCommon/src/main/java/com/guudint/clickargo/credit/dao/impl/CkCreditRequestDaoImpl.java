package com.guudint.clickargo.credit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.credit.dao.CkCreditRequestDao;
import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkCreditRequestDaoImpl extends GenericDaoImpl<TCkCreditRequest, String> implements CkCreditRequestDao {

    @Override
    public List<TCkCreditRequest> findByServiceTypeAndAccnId(String serviceType, String accnId) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkCreditRequest.class);
        criteria.add(Restrictions.eq("TCkMstServiceType.svctId", serviceType));
        criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
        criteria.add(Restrictions.eq("cruStatus", Constant.ACTIVE_STATUS));
        return getByCriteria(criteria);
    }

    @Override
    public List<TCkCreditRequest> findByStateAndStartDt(String state, String dateStart) throws Exception {
        String hql = "from TCkCreditRequest o "
                + "where o.TCkMstCreditRequestState.stId = :state and DATE_FORMAT(o.cruDtStart,'%d%m%Y') = :dateStart";
        Map<String, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("dateStart", dateStart);
        return getByQuery(hql, param);
    }

}
