/**
 * 
 */
package com.guudint.clickargo.payment.dao;

import java.util.Date;
import java.util.List;

import com.guudint.clickargo.payment.model.TCkPaymentTxn;
import com.vcc.camelone.common.dao.GenericDao;

/**
 * @author billy
 *
 */
public interface CkPaymentTxnDao extends GenericDao<TCkPaymentTxn, String> {

    TCkPaymentTxn getByIdAndStatus(String id, char status);

    TCkPaymentTxn getByBankRefAndStatus(String id, char status);

    List<TCkPaymentTxn> getByPaymentTypePaidAndStatus(String ptyId, Date beginDateTime, Date endDateTime, char status);

    List<TCkPaymentTxn> findByJobId(String jobId) throws Exception;

    List<TCkPaymentTxn> findByIds(List<String> ids) throws Exception;

    List<TCkPaymentTxn> findByPtxPayee(String accnId) throws Exception;
    
    List<TCkPaymentTxn>findByVa(String vaNumber) throws Exception;

    Date findDueDate(String ptxId) throws Exception;
}
