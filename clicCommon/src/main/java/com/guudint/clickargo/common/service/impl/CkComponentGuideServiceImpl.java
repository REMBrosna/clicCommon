/**
 * 
 */
package com.guudint.clickargo.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.guudint.clickargo.common.dto.CkComponentGuide;
import com.guudint.clickargo.common.model.TCkComponentGuide;
import com.guudint.clickargo.common.service.ICkCompGuide;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

/**
 * @author billy
 *
 */
public class CkComponentGuideServiceImpl implements ICkCompGuide {

	// Static Attributes
	////////////////////
	private static Logger log = LogManager.getLogger(CkComponentGuideServiceImpl.class);

	// Attributes
	/////////////
	@Autowired
	private GenericDao<TCkComponentGuide, String> ckComponentGuideDao;

	// Interface Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.common.service.ICkCompGuide#getCompGuides(java.lang.String,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	public List<CkComponentGuide> getCompGuides(String componentIdPath, Principal principal)
			throws ParameterException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("getCompGuides");

		if (StringUtils.isEmpty(componentIdPath))
			throw new ParameterException("param componentIdPath null or empty");
		if (null == principal)
			throw new ParameterException("param principal null");

		String hql = "FROM TCkComponentGuide x where x.cmguComponentId "
				+ "like :componentIdPath and x.cmguStatus = :cmguStatus"
				+ " and x.TCoreApps.appsCode=:appsCode";
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("componentIdPath", componentIdPath + "%");
		parameters.put("cmguStatus", Constant.ACTIVE_STATUS);
		parameters.put("appsCode", principal.getAppsCode());
		List<TCkComponentGuide> tckCompGuides = ckComponentGuideDao.getByQuery(hql, parameters);
		List<CkComponentGuide> ckCompGuides = tckCompGuides.stream().map(x -> new CkComponentGuide(x))
				.collect(Collectors.toList());
		return ckCompGuides;
	}

}
