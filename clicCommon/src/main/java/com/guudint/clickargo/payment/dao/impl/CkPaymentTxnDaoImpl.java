/**
 * 
 */
package com.guudint.clickargo.payment.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.enums.JobStates;
import com.guudint.clickargo.payment.dao.CkPaymentTxnDao;
import com.guudint.clickargo.payment.enums.PaymentStates;
import com.guudint.clickargo.payment.model.TCkPaymentTxn;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkPaymentTxnDaoImpl extends GenericDaoImpl<TCkPaymentTxn, String> implements CkPaymentTxnDao {

	@Override
	@Transactional(readOnly = true)
	public TCkPaymentTxn getByIdAndStatus(String id, char status) {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(TCkPaymentTxn.class);
			dc.add(Restrictions.eq("ptxId", id));
			dc.add(Restrictions.eq("ptxStatus", status));
			return getOne(dc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TCkPaymentTxn getByBankRefAndStatus(String bankRef, char status) {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(TCkPaymentTxn.class);
			dc.add(Restrictions.eq("ptxBankRef", bankRef));
			dc.add(Restrictions.eq("ptxStatus", status));
			return getOne(dc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Payee, a person to whom money is paid
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TCkPaymentTxn> getByPaymentTypePaidAndStatus(String ptyId, Date beginDateTime, Date endDateTime,
			char status) {

		try {
			DetachedCriteria dc = DetachedCriteria.forClass(TCkPaymentTxn.class);
			dc.add(Restrictions.eq("TCkMstPaymentType.ptyId", ptyId));
			dc.add(Restrictions.ge("ptxDtPaid", beginDateTime));
			dc.add(Restrictions.lt("ptxDtPaid", endDateTime));
			dc.add(Restrictions.eq("ptxPaymentState", JobStates.PAID.name()));
			dc.add(Restrictions.eq("ptxStatus", status));
			return getByCriteria(dc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<TCkPaymentTxn> findByJobId(String jobId) throws Exception {
		String hql = "from TCkPaymentTxn ptx where ptxSvcRef = :jobId "
				+ "AND ptxStatus = :status ";

		Map<String, Object> params = new HashMap<>();
		params.put("jobId", jobId);
		params.put("status", RecordStatus.ACTIVE.getCode());

		return getByQuery(hql, params);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TCkPaymentTxn> findByIds(List<String> ids) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkPaymentTxn.class);
		criteria.add(Restrictions.in("ptxId", ids));
		return getByCriteria(criteria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TCkPaymentTxn> findByPtxPayee(String accnId) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkPaymentTxn.class);
		criteria.add(Restrictions.eq("TCoreAccnByPtxPayee.accnId", accnId));
		return getByCriteria(criteria);
	}

	@Override
	public List<TCkPaymentTxn> findByVa(String vaNumber) throws Exception {
		String hql = "from TCkPaymentTxn ptx where ptxPayerBankAccn LIKE :vaNumber "
				+ "AND ptxPaymentState = :paymentState "
				+ "AND ptxStatus = :status ";

		Map<String, Object> params = new HashMap<>();
		params.put("vaNumber", "%" + vaNumber + "%");
		params.put("paymentState", PaymentStates.PAYING.getCode());
		params.put("status", RecordStatus.ACTIVE.getCode());
		
		return getByQuery(hql, params);
	}

	@Override
	public Date findDueDate(String ptxId) throws Exception {
		String sql = "select distinct min(DATE_FORMAT(tcjt.JOB_IN_PAYMENT_DT_DUE,'%Y-%m-%d')) due_date "
				+ "from clickargo2.T_CK_PAYMENT_TXN tcp "
				+ "inner join clickargo2.T_CK_JOB_TRUCK tcjt on tcp.PTX_SVC_REF like concat('%',tcjt.JOB_ID ,'%') "
				+ "where tcp.PTX_PAYMENT_STATE <>'CANCELLED' and PTX_ID = :ptxId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("ptxId", ptxId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(query.uniqueResult() == null){
			return null;
		}
		return sdf.parse(query.uniqueResult().toString());
	}

}
