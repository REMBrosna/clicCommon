package com.guudint.clickargo.tax.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.tax.dao.CkTaxSeqDao;
import com.guudint.clickargo.tax.model.TCkTaxSeq;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;

@Service
public class CkTaxSeqService {

	private static Logger LOG = Logger.getLogger(CkTaxSeqService.class);
	
	@Autowired
	CkTaxSeqDao ckTaxSeqDao;
	@Autowired
	protected ICkSession ckSession;
	
	public static char TAX_SEQ_STATUS_EXPIRE = 'E';


	@Transactional
	public synchronized String getNextSageTaxSequence(ServiceTypes serviceType) throws Exception {
		
		try {

			List<TCkTaxSeq> ctList  = ckTaxSeqDao.findTaxSeq(serviceType.name());
			
			if( ctList == null || ctList.size() == 0) {
				throw new Exception("No active Sage Tax data, for service type: " + serviceType);
			}
			
			TCkTaxSeq tCkCtSageTaxEx = null;
			String resultStr = "";

			for (TCkTaxSeq tCkCtSageTax : ctList) {
				if(RecordStatus.ACTIVE.getCode() == tCkCtSageTax.getTsStatus()) {
					if(tCkCtSageTax.getTsRangeCurrent() == tCkCtSageTax.getTsRangeEnd()) {
						tCkCtSageTax.setTsStatus(TAX_SEQ_STATUS_EXPIRE);
						tCkCtSageTaxEx = tCkCtSageTax;
					} else {
						tCkCtSageTax.setTsRangeCurrent(tCkCtSageTax.getTsRangeCurrent()+1);
						resultStr = tCkCtSageTax.getTsPrefix() + String.format(tCkCtSageTax.getTsRangeFormat(), tCkCtSageTax.getTsRangeCurrent());
						break;
					}
					ckTaxSeqDao.update(tCkCtSageTax);
				}
			}
			
			if(tCkCtSageTaxEx != null) {
				for (TCkTaxSeq tCkCtSageTax : ctList) {
					if(RecordStatus.INACTIVE.getCode() == tCkCtSageTax.getTsStatus()) {
						tCkCtSageTax.setTsStatus(RecordStatus.ACTIVE.getCode());
						ckTaxSeqDao.update(tCkCtSageTax);
						resultStr = tCkCtSageTax.getTsPrefix() + String.format(tCkCtSageTax.getTsRangeFormat(), tCkCtSageTax.getTsRangeCurrent());
						break;
					}
				}
			}
			return resultStr;
		} catch (Exception ex) {
			throw ex;
		}
	}
	

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCkTaxSeq updateStatus(String id, String status)
			throws Exception {
		LOG.info("updateStatus " + id);
		Principal principal = ckSession.getPrincipal();
		if (principal == null) {
			throw new ParameterException("principal is null");
		}
		
		TCkTaxSeq taxSeq = ckTaxSeqDao.find(id);
		if (taxSeq == null) {
			throw new EntityNotFoundException("id::" + id);
		}
		
		if ("active".equals(status)) {
			taxSeq.setTsStatus(RecordStatus.ACTIVE.getCode());
		} else if ("inactive".equals(status)) {
			taxSeq.setTsStatus(RecordStatus.INACTIVE.getCode());
		}else if("delete".equals(status)){
			if(taxSeq.getTsStatus().equals(RecordStatus.INACTIVE.getCode())) {
				taxSeq.setTsStatus(RecordStatus.DEACTIVATE.getCode());
			}
			else if (taxSeq.getTsStatus().equals(RecordStatus.ACTIVE.getCode())) {
				throw new ParameterException("Sage Tax id : " + id + " is ACTIVE");
			}
		}
		
		
		return taxSeq;
	}
}
