package com.guudint.clickargo.common.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

/**
 * Interface just for listing purpose. If need CRUD, please use
 * {@code IEntityService} or extends {@code AbstractEntityService}
 */
public interface ICkListingService<E, K, D> {

	/**
	 * Required by Richard but will not work with entity having complex key
	 * 
	 * @param id
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<D> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException;

	/**
	 * Count all the entities by criteria
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int countByAnd(D dto) throws ParameterException, EntityNotFoundException, ProcessingException;

}
