/**
 * 
 */
package com.guudint.clickargo.master.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.guudint.clickargo.master.service.ICkMstRecords;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

/**
 * @author billy
 *
 */
public class CkMstRecordsSvcImpl implements ICkMstRecords {

	// Static Attributes
	////////////////////
	private static Logger log = LogManager.getLogger(CkMstRecordsSvcImpl.class);

	@Autowired
	protected ApplicationContext applicationContext;
	protected HashMap<String, String> daoBeans;
	protected HashMap<String, String> idBeans;
	protected HashMap<String, HashMap<String, Object>> masterBeans;

	// Interface Methods
	////////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.master.service.ICkMstRecords#getRecords(java.lang.String)
	 * 
	 */
	public HashMap<String, Object> getRecords(String recordType)
			throws ParameterException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("getRecords");

		if (StringUtils.isEmpty(recordType))
			throw new ParameterException("param recrodType null or empty");
		if (!daoBeans.containsKey(recordType))
			throw new ProcessingException("param recrodType undefined in daoBeans: " + recordType);
		if (!idBeans.containsKey(recordType))
			throw new ProcessingException("param recrodType undefined in idBeans: " + recordType);

		try {
			if (null == masterBeans)
				masterBeans = new HashMap<String, HashMap<String, Object>>();

			if (!masterBeans.containsKey(recordType)) {
				@SuppressWarnings("unchecked")
				GenericDaoImpl<COAbstractEntity<?>, String> daoBean = (GenericDaoImpl<COAbstractEntity<?>, String>) applicationContext
						.getBean(daoBeans.get(recordType));
				List<COAbstractEntity<?>> beans = daoBean.getAll();

				HashMap<String, Object> hmMasterBeans = new HashMap<>();
				for (COAbstractEntity<?> bean : beans) {
					Method getIdMethod = bean.getClass().getMethod(idBeans.get(recordType));
					String beanId = (String) getIdMethod.invoke(bean);
					hmMasterBeans.put(beanId, bean);
				}
				masterBeans.put(recordType, hmMasterBeans);
			}
			return masterBeans.get(recordType);

		} catch (ProcessingException ex) {
			log.error("getRecords", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getRecords", ex);
			throw new ProcessingException(ex);
		}
	}

	// Properties
	/////////////
	/**
	 * @return the daoBeans
	 */
	public HashMap<String, String> getDaoBeans() {
		return daoBeans;
	}

	/**
	 * @param daoBeans the daoBeans to set
	 */
	public void setDaoBeans(HashMap<String, String> daoBeans) {
		this.daoBeans = daoBeans;
	}

	/**
	 * @return the idBeans
	 */
	public HashMap<String, String> getIdBeans() {
		return idBeans;
	}

	/**
	 * @param idBeans the idBeans to set
	 */
	public void setIdBeans(HashMap<String, String> idBeans) {
		this.idBeans = idBeans;
	}

}
