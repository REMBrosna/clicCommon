package com.guudint.clickargo.common.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.util.PrincipalUtilService;

/**
 * This listing entity service is mainly for listing purposes only.
 */
@Service
public abstract class AbstractCkListingService<E, K, D> implements ICkListingService<E, K, D> {

	// Static Attributes
	/////////////////////
	private static Logger log = Logger.getLogger(AbstractCkListingService.class);

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected PrincipalUtilService principalUtilService;

	@Autowired
	protected GenericDao<E, K> dao;

	protected String daoName;
	protected String moduleName;
	protected String entityName;
	protected String tableName;

	/**
	 * @param daoName
	 * @param moduleName
	 * @param entityName
	 */
	public AbstractCkListingService(String daoName, String moduleName, String entityName) {
		log.debug("AbstractCkListingService");
		try {
			this.daoName = daoName;
			this.moduleName = moduleName;
			this.entityName = entityName;
		} catch (Exception ex) {
			log.error("AbstractCkListingService", ex);
		}
	}

	/**
	 * @param daoName
	 * @param moduleName
	 * @param entityName
	 * @param tableName
	 */
	public AbstractCkListingService(String daoName, String moduleName, String entityName, String tableName) {
		log.debug("AbstractCkListingService");
		try {
			this.daoName = daoName;
			this.moduleName = moduleName;
			this.entityName = entityName;
			this.tableName = tableName;
		} catch (Exception ex) {
			log.error("AbstractCkListingService", ex);
		}
	}

	/**
	 * Post creator initialize the dao
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	protected void init() {
		log.debug("init");

		try {
			this.dao = (GenericDao<E, K>) applicationContext.getBean(daoName);
		} catch (Exception ex) {
			log.error("init", ex);
		}
	}

	// Abstract Methods - IEntityService
	////////////////////////////////////
	/**
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected abstract E initEnity(E entity) throws ParameterException, ProcessingException;

	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	protected abstract D dtoFromEntity(E entity) throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @param wherePrinted
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected abstract String getWhereClause(D dto, boolean wherePrinted)
			throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected abstract HashMap<String, Object> getParameters(D dto) throws ParameterException, ProcessingException;

	/**
	 * @param filterRequest
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected abstract D whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.service.entity.IEntityService#countByAnd(java.lang.Object)
	 */
	@Override
	public int countByAnd(D dto) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("countByAnd");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			String whereClause = this.getWhereClause(dto, false); // abstract callback
			HashMap<String, Object> parameters = this.getParameters(dto); // abstract callback

			int count = 0;
			if (StringUtils.isNotEmpty(whereClause) && null != parameters && parameters.size() > 0) {
				count = this.dao.count("SELECT COUNT(o) FROM " + this.entityName + " o" + whereClause, parameters);
			} else {
				count = this.dao.count("SELECT COUNT(o) FROM " + this.entityName + " o");
			}
			return count;
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("countByAnd", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("countByAnd", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected List<E> findEntitiesByAnd(D dto, String selectClause, String orderByClause, int limit, int offset)
			throws ParameterException, ProcessingException {
		log.debug("findEntitesByAnd");
		try {
			if (null == dto)
				throw new ParameterException("param dto null");
			if (StringUtils.isEmpty(selectClause))
				throw new ParameterException("param selectClause null or empty");
			if (StringUtils.isEmpty(orderByClause))
				throw new ParameterException("param orderByClause null or empty");

			String whereClause = this.getWhereClause(dto, false); // abstract callback
			log.debug("whereClause: " + whereClause);
			HashMap<String, Object> parameters = this.getParameters(dto); // abstract callback

			String hqlQuery = StringUtils.isEmpty(whereClause) ? selectClause + orderByClause
					: selectClause + whereClause + orderByClause;

			List<E> entities = dao.getByQuery(hqlQuery, parameters, limit, offset);
			for (E entity : entities)
				this.initEnity(entity); // abstract callback
			return entities;
		} catch (ParameterException | ProcessingException ex) {
			log.error("findEntitiesByAnd", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("findEntitiesByAnd", ex);
			throw new ProcessingException(ex);
		}
	}


	/**
	 * Allows formatting to the orderByClause string if the variable used from dto
	 * is different from the entity class
	 * 
	 * e.g. (TPediVcform) TPediAppVcGeneral TPediAppVcGeneral !=
	 * (PediVcForm)PediAppVcGeneral pediAppVcGeneral;
	 * 
	 * If not specified it will return the original orderByClause
	 * 
	 * This is applicable for all the columns visible in the datatable listing.
	 */
	protected abstract String formatOrderBy(String attribute) throws Exception;

	protected EntityOrderBy formatOrderByObj(EntityOrderBy orderBy) throws Exception {

		if (orderBy == null)
			return null;

		if (StringUtils.isEmpty(orderBy.getAttribute()))
			return null;

		String newAttr = formatOrderBy(orderBy.getAttribute());
		if (StringUtils.isEmpty(newAttr))
			return orderBy;

		orderBy.setAttribute(newAttr);
		return orderBy;

	}

	protected String getOperator(boolean whereprinted) {
		return whereprinted ? " AND " : " WHERE ";
	}
}
