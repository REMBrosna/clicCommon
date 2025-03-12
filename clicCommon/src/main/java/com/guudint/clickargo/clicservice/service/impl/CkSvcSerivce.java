package com.guudint.clickargo.clicservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.dao.CkSvcSubDao;
import com.guudint.clickargo.clicservice.dto.CkSvcSub;
import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.guudint.clickargo.master.dao.CkMstServiceTypeDao;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstServiceType;

@Service
public class CkSvcSerivce {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkSvcSerivce.class);

	@Autowired
	private CkSvcSubDao svcSubDao;

	@Autowired
	private CkMstServiceTypeDao mstServiceTypeDao;

	// Interface Methods
	/////////////////////

	/**
	 * Retrieves the subscribed services of the {@code accnId}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkSvcSub> findSubscribedAppSvc(String accnId) throws Exception {

		try {
			List<TCkSvcSub> tSvcSubList = svcSubDao.findSubscribedAppSvc(accnId);

			List<CkSvcSub> svcSubList = tSvcSubList.stream().map(o -> entity2Dto(o)).collect(Collectors.toList());

			log.info("" + svcSubList);

			return svcSubList;
		} catch (Exception ex) {
			log.error("findAppSvc", ex);
			throw ex;
		}
	}

	/**
	 * Retrieves all active services but modified to include a flag if the
	 * {@code accnId} is subscribed to the service.
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkMstServiceType> getServices(String accnId) throws Exception {

		try {

			// Load all the services
			List<TCkMstServiceType> serviceTypes = mstServiceTypeDao.getAllActive();
			List<CkMstServiceType> listServiceTypes = serviceTypes.stream().map(e -> new CkMstServiceType(e))
					.collect(Collectors.toList());

			// Get all the subscribed services
			List<TCkSvcSub> tSvcSubList = svcSubDao.findSubscribedAppSvc(accnId);
			tSvcSubList.stream().forEach(e -> {
				Hibernate.initialize(e.getTCkMstServiceType());
				Hibernate.initialize(e.getTCkMstSvcSubState());
				Hibernate.initialize(e.getTCoreAccn());
			});

			// Iterate through the services and check if the element is in the subscription
			List<CkMstServiceType> updatedSvcTypes = listServiceTypes.stream().map(e -> {
				if (tSvcSubList.stream()
						.anyMatch(sub -> sub.getTCkMstServiceType().getSvctId().equalsIgnoreCase(e.getSvctId()))) {
					e.setIsSubscribed(true);
				} else {
					e.setIsSubscribed(false);
				}
				return e;
			}).collect(Collectors.toList());

			return updatedSvcTypes;
		} catch (Exception ex) {
			log.error("getServices", ex);
			throw ex;
		}
	}

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkMstServiceType> getActiveServices() throws Exception {

		try {

			// Load all the services
			List<TCkMstServiceType> serviceTypes = mstServiceTypeDao.getAllActive();
			List<CkMstServiceType> listServiceTypes = serviceTypes.stream().map(e -> new CkMstServiceType(e))
					.collect(Collectors.toList());

			return listServiceTypes;
		} catch (Exception ex) {
			log.error("getServices", ex);
			throw ex;
		}
	}

	private CkSvcSub entity2Dto(TCkSvcSub entity) {
		Hibernate.initialize(entity.getTCkMstServiceType());
		
		CkSvcSub svcSub = new CkSvcSub(entity);
		svcSub.setTCkMstServiceType(new CkMstServiceType(entity.getTCkMstServiceType()));
		return svcSub;
	}

}
