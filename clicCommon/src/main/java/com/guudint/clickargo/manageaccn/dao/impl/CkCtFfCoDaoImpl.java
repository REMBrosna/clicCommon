package com.guudint.clickargo.manageaccn.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.manageaccn.dao.CkCtFfCoDao;
import com.guudint.clickargo.manageaccn.model.TCkCtFfCo;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Service("ckCtFfCoDao")
public class CkCtFfCoDaoImpl extends GenericDaoImpl<TCkCtFfCo, String>
		implements CkCtFfCoDao {

	@Override
	public List<TCkCtFfCo> getAccnByFfAccnId(String ffAccnId) throws Exception {

		String hql = "from TCkCtFfCo ffco " 
				+ " where ffco.TCoreAccnByFfcoCo.accnStatus = :accnStatus "
				+ " 	and ffco.TCoreAccnByFfcoFf.accnId =:accnId "
				+ " 	and ( ffco.ffcoDtStart is null or ffco.ffcoDtStart <= :ffcoDtStart)"
				+ " 	and ( ffco.ffcoDtEnd is null or ffco.ffcoDtEnd >= :ffcoDtEnd)"
				+ " order by ffco.TCoreAccnByFfcoCo.accnName asc";
		

		Map<String, Object> params = new HashMap<>();
		params.put("accnStatus", Arrays.asList(RecordStatus.ACTIVE.getCode()));
		params.put("accnId", ffAccnId);
		params.put("ffcoDtStart", new Date());
		params.put("ffcoDtEnd", new Date());
		
		return getByQuery(hql, params);
	}
}
