package com.guudint.clickargo.tax.dao;

import java.util.List;
import com.guudint.clickargo.tax.model.TCkTaxInvoice;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkTaxInvoiceDao extends GenericDao<TCkTaxInvoice, String> {

    List<TCkTaxInvoice> findByFakturNumber(String fakturNumber) throws Exception;

    List<TCkTaxInvoice> findByTiInvDtIssue(String date) throws Exception;

    List<TCkTaxInvoice> findByJobId(String jobId) throws Exception;

    List<TCkTaxInvoice> findByJobIdAndInvNo(String jobId, String invNo) throws Exception;

    List<TCkTaxInvoice> findByStatus(Character status) throws Exception;
    
    public List<TCkTaxInvoice> findByServiceAndStatus(String service, Character status) throws Exception;
}
