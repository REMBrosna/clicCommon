/**
 *
 */
package com.guudint.clickargo.master.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.master.dao.CkMstPaymentAuditTypeDao;
import com.guudint.clickargo.master.model.TCkMstPaymentAuditType;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

/**
 * @author billy
 *
 */
public class CkMstPaymentAuditTypeDaoImpl extends GenericDaoImpl<TCkMstPaymentAuditType, String>
		implements CkMstPaymentAuditTypeDao {

	@Override
	@Transactional(readOnly = true)
	public TCkMstPaymentAuditType findByIdAndStatus(String id, char status) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("id", id);
			paramMap.put("status", status);

			List<TCkMstPaymentAuditType> tCkMstPaymentAuditTypeList = getByQuery(
					"select o from TCkMstPaymentAuditType o where patyId = :id and pytStatus = :status", paramMap);
			return tCkMstPaymentAuditTypeList.isEmpty() ? null : tCkMstPaymentAuditTypeList.get(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
