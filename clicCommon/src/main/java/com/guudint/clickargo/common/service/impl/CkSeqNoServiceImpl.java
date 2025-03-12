package com.guudint.clickargo.common.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.model.TCkSequenceNo;
import com.guudint.clickargo.common.service.ICkSeqNoService;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;

@Service
public class CkSeqNoServiceImpl implements ICkSeqNoService {

	public static enum SeqNoCode {
		CT_PF_INV_NO,
		CT_DN_NO,
		CT_SAGE_BATCH_NO,
		CT_ACCN_CODE,
		CT_USER_CODE,
		CT_SHELL_INV_NO
	};

	@Autowired
	private GenericDao<TCkSequenceNo, String> ckSeqNoDao;

	@Override
	public synchronized String getNextSequence(String id) throws Exception {
		try {
			if (StringUtils.isBlank(id))
				throw new ParameterException("param id null or empty");

			TCkSequenceNo seq = ckSeqNoDao.find(id);
			if (seq != null) {
				// update before your return
				String result = String.format(seq.getSqnKey(), seq.getSqnSeq());
				seq.setSqnSeq(seq.getSqnSeq() + 1);
				ckSeqNoDao.update(seq);
				return result;
			}

		} catch (Exception ex) {
			throw ex;
		}

		return null;

	}

}
