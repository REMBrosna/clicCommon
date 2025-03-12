package com.guudint.clickargo.tax.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.tax.dao.CkTaxInvoiceDao;
import com.guudint.clickargo.tax.model.TCkTaxInvoice;
import com.guudint.clickargo.tax.service.CkTaxInvoiceEntityServiceImpl;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Service("ckTaxInvoiceDao")
public class CkTaxInvoiceDaoImpl extends GenericDaoImpl<TCkTaxInvoice, String> implements CkTaxInvoiceDao {


    @Override
    public List<TCkTaxInvoice> findByTiInvDtIssue(String date) throws Exception {
        String hql = "from TCkTaxInvoice ti "
                + "where DATE_FORMAT(ti.tiInvDtIssue, '%d/%m/%Y') = :date";
        Map<String, Object> params = new HashMap<>();
        params.put("date", date);
        return getByQuery(hql, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public List<TCkTaxInvoice> findByFakturNumber(String fakturNumber) throws Exception {
        String hql = "from TCkTaxInvoice ti "
                + " 	where replace(replace(ti.tiNo, '.', ''), '-', '') = :fakturNumber";

        Map<String, Object> params = new HashMap<>();
        params.put("fakturNumber", fakturNumber);

        return getByQuery(hql, params);
    }

    @Override
    public List<TCkTaxInvoice> findByJobId(String jobId) throws Exception {
        String hql = "from TCkTaxInvoice ti "
                + "where ti.tiStatus = :status and ti.tiJobNo = :jobId";
        Map<String, Object> params = new HashMap<>();
        params.put("status", CkTaxInvoiceEntityServiceImpl.TAX_INVOICE_STATUS_COMPLETED);
        params.put("jobId", jobId);
        return getByQuery(hql, params);
    }

    @Override
    public List<TCkTaxInvoice> findByJobIdAndInvNo(String jobId, String invNo) throws Exception {
        DetachedCriteria criterion = DetachedCriteria.forClass(TCkTaxInvoice.class);
        criterion.add(Restrictions.eq("tiJobNo", jobId));
        criterion.add(Restrictions.eq("tiInvNo", invNo));
        return getByCriteria(criterion);
    }

    @Override
    public List<TCkTaxInvoice> findByStatus(Character status) throws Exception {
        DetachedCriteria criterion = DetachedCriteria.forClass(TCkTaxInvoice.class);
        criterion.add(Restrictions.eq("tiStatus", status));
        return getByCriteria(criterion);
    }

    @Override
    public List<TCkTaxInvoice> findByServiceAndStatus(String service, Character status) throws Exception {
        DetachedCriteria criterion = DetachedCriteria.forClass(TCkTaxInvoice.class);
        criterion.add(Restrictions.eq("tiService", service));
        criterion.add(Restrictions.eq("tiStatus", status));
        return getByCriteria(criterion);
    }
}
