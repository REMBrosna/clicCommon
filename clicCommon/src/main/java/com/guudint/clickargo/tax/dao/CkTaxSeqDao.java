package com.guudint.clickargo.tax.dao;

import java.util.List;

import com.guudint.clickargo.tax.model.TCkTaxSeq;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkTaxSeqDao extends GenericDao<TCkTaxSeq, String>{
    
	public List<TCkTaxSeq> findTaxSeq(String serviceType) throws Exception;
}
