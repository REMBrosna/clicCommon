package com.guudint.clickargo.tax.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.tax.dao.CkTaxSeqDao;
import com.guudint.clickargo.tax.model.TCkTaxSeq;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Service("ckTaxSeqDao")
public class CkTaxSeqDaoImpl extends GenericDaoImpl<TCkTaxSeq, String> implements CkTaxSeqDao {

	
	public List<TCkTaxSeq> findTaxSeq(String serviceType) throws Exception {

		String hql = "from TCkTaxSeq ts " 
				+ "where ts.tsStatus IN :tsStatus "
				+ " and ts.tsService = :tsService "
				+ " order by ts.tsDtCreate asc";
		

		Map<String, Object> params = new HashMap<>();
		params.put("tsStatus", Arrays.asList(RecordStatus.ACTIVE.getCode(), RecordStatus.INACTIVE.getCode()));
		params.put("tsService", serviceType);

		return getByQuery(hql, params, 2, 0);
	}
	
}
