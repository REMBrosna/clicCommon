package com.guudint.clickargo.manageaccn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkMstRemarkType;
import com.guudint.clickargo.common.dto.CkMstWorkflowType;
import com.guudint.clickargo.common.dto.CkWorkflowRemark;
import com.guudint.clickargo.common.model.TCkMstRemarkType;
import com.guudint.clickargo.common.model.TCkMstWorkflowType;
import com.guudint.clickargo.common.model.TCkWorkflowRemark;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkWorkflowRemarkService extends AbstractClickCargoEntityService<TCkWorkflowRemark, String, CkWorkflowRemark>{

	public CkWorkflowRemarkService() {
		super("ckWorkflowRemarkDao", "ACCOUNT REMARK", TCkWorkflowRemark.class.getName(), "T_CK_WORKFLOW_REMARK");
	}
	
	private static Logger LOG = Logger.getLogger(CkWorkflowRemarkService.class);
	private static final String PREFIX_KEY = "CKCTAR";
	
	@Override
	public CkWorkflowRemark newObj(Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		CkWorkflowRemark wfRmk =  new CkWorkflowRemark();
		CoreAccn accn = new CoreAccn();
		CkMstRemarkType rmkType = new CkMstRemarkType();
		CkMstWorkflowType wfType = new CkMstWorkflowType();
		wfRmk.setTCoreAccn(accn);
		wfRmk.setTCkMstRemarkType(rmkType);
		wfRmk.setTCkMstWorkflowType(wfType);
		return wfRmk;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkWorkflowRemark findById(String id)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("findById");
		if (StringUtils.isEmpty(id)) {
			throw new ParameterException("param id null or empty");
		}
		try {
			TCkWorkflowRemark tCkWorkflowRemark = dao.find(id);
			if (tCkWorkflowRemark == null) {
				throw new EntityNotFoundException("findById -> id:" + id);
			}
			initEnity(tCkWorkflowRemark);
			return dtoFromEntity(tCkWorkflowRemark);
		} catch (Exception e) {
			LOG.error("findById" + e);
		}
		return null;
	}

	@Override
	public CkWorkflowRemark deleteById(String paramString, Principal paramPrincipal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkWorkflowRemark> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("filterBy");
		if (filterRequest == null) {
			throw new ParameterException("param filterRequest null");
		}
		CkWorkflowRemark CkWorkflowRemark = whereDto(filterRequest);
		if (CkWorkflowRemark == null) {
			throw new ProcessingException("whereDto null result");
		}
		
		filterRequest.setTotalRecords(countByAnd(CkWorkflowRemark));
		List<CkWorkflowRemark> CkWorkflowRemarks = new ArrayList<>();
		try {
			String orderClause = formatOrderByObj(filterRequest.getOrderBy()).toString();
			List<TCkWorkflowRemark> tCkWorkflowRemarks = findEntitiesByAnd(CkWorkflowRemark, "from TCkWorkflowRemark o ", orderClause, filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			for (TCkWorkflowRemark tCkWorkflowRemark : tCkWorkflowRemarks) {
				CkWorkflowRemark dto = dtoFromEntity(tCkWorkflowRemark);
				if (dto != null) {
					CkWorkflowRemarks.add(dto);
				}
			}
		} catch (Exception e) {
			LOG.error("filterBy", e);
		}
		return CkWorkflowRemarks;
	}
	
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
	
	protected String formatOrderBy(String attributes) throws ParameterException, ProcessingException, Exception {
        LOG.debug("formatOrderBy");
        String attribute = attributes;

        if (StringUtils.contains(attribute, "tckMstWorkflowType"))
            attribute = attribute.replace("tckMstWorkflowType", "TCkMstWorkflowType");
        
        if (StringUtils.contains(attribute, "tckMstRemarkType"))
            attribute = attribute.replace("tckMstRemarkType", "TCkMstRemarkType");
        
        if (StringUtils.contains(attribute, "tcoreAccn"))
            attribute = attribute.replace("tcoreAccn", "TCoreAccn");

        return attribute;
    }


	@Override
	protected void initBusinessValidator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return LOG;
	}

	@Override
	protected TCkWorkflowRemark initEnity(TCkWorkflowRemark entity)
			throws ParameterException, ProcessingException {
		LOG.debug("initEnity");
		if (null != entity) {
			Hibernate.initialize(entity.getTCkMstRemarkType());
			Hibernate.initialize(entity.getTCkMstWorkflowType());
			Hibernate.initialize(entity.getTCoreAccn());
		}
		return entity;
	}

	@Override
	protected TCkWorkflowRemark entityFromDTO(CkWorkflowRemark dto)
			throws ParameterException, ProcessingException {
		LOG.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkWorkflowRemark entity = new TCkWorkflowRemark();
			entity = dto.toEntity(entity);

			Optional<TCkMstRemarkType> opCkMstRemarkType = Optional.ofNullable(entity.getTCkMstRemarkType());
			entity.setTCkMstRemarkType(opCkMstRemarkType.isPresent() ? opCkMstRemarkType.get() : null);
			
			Optional<TCkMstWorkflowType> opCkMstWorkflowType = Optional.ofNullable(entity.getTCkMstWorkflowType());
			entity.setTCkMstWorkflowType(opCkMstWorkflowType.isPresent() ? opCkMstWorkflowType.get() : null);

			Optional<TCoreAccn> opCoreAccn = Optional.ofNullable(entity.getTCoreAccn());
			entity.setTCoreAccn(opCoreAccn.isPresent() ? opCoreAccn.get() : null);

			return entity;
		} catch (ParameterException ex) {
			LOG.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("entityFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected CkWorkflowRemark dtoFromEntity(TCkWorkflowRemark entity)
			throws ParameterException, ProcessingException {
		LOG.debug("dtoFromEntity");
		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkWorkflowRemark dto = new CkWorkflowRemark(entity);
			
			Optional<TCoreAccn> opCoreAccn = Optional.ofNullable(entity.getTCoreAccn());
			dto.setTCoreAccn(opCoreAccn.isPresent() ? new CoreAccn(opCoreAccn.get()) : null);
			
			Optional<TCkMstRemarkType> opCkMstRemarkType = Optional.ofNullable(entity.getTCkMstRemarkType());
			dto.setTCkMstRemarkType(opCkMstRemarkType.isPresent() ? new CkMstRemarkType(opCkMstRemarkType.get()) : null);
			
			Optional<TCkMstWorkflowType> opCkMstWorkflowType = Optional.ofNullable(entity.getTCkMstWorkflowType());
			dto.setTCkMstWorkflowType(opCkMstWorkflowType.isPresent() ? new CkMstWorkflowType(opCkMstWorkflowType.get()) : null);
			
			return dto;

		} catch (ParameterException ex) {
			LOG.error("dtoFromEntity", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}

	}

	@Override
	protected String entityKeyFromDTO(CkWorkflowRemark dto) throws ParameterException, ProcessingException {
		LOG.debug("entityKeyFromDTO");
		if (null == dto)
			throw new ParameterException("dto param null");

		return dto.getArId();
	}

	@Override
	protected TCkWorkflowRemark updateEntity(ACTION attribute, TCkWorkflowRemark entity,
			Principal principal, Date date) throws ParameterException, ProcessingException {
		LOG.debug("updateEntity");
		if (null == entity)
			throw new ParameterException("param entity null");
		if (null == principal)
			throw new ParameterException("param principal null");
		if (null == date)
			throw new ParameterException("param date null");

		Optional<String> opUserId = Optional.ofNullable(principal.getUserId());
		switch (attribute) {
		case CREATE:
			entity.setArId(CkUtil.generateId(PREFIX_KEY));
			entity.setAtUidCreate(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
			entity.setAtStatus(RecordStatus.ACTIVE.getCode());
			entity.setAtDtCreate(date);
			entity.setAtDtLupd(date);
			entity.setAtUidLupd(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
			break;

		default:
			break;
		}

		return entity;	}

	@Override
	protected TCkWorkflowRemark updateEntityStatus(TCkWorkflowRemark paramE, char paramChar)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkWorkflowRemark preSaveUpdateDTO(TCkWorkflowRemark paramE, CkWorkflowRemark paramD)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void preSaveValidation(CkWorkflowRemark paramD, Principal paramPrincipal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ServiceStatus preUpdateValidation(CkWorkflowRemark paramD, Principal paramPrincipal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkWorkflowRemark dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");

		if (null == dto)
			throw new ParameterException("param dto null");

		StringBuffer searchStatement = new StringBuffer();
			
		if (StringUtils.isNotBlank(dto.getArRemark())) {
			searchStatement.append(getOperator(wherePrinted) + "o.arRemark LIKE :arRemark");
			wherePrinted = true;
		}
		
		if (null != dto.getAtDtCreate()) {
			searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.atDtCreate,'%d/%m/%Y') = :atDtCreate");
			wherePrinted = true;
		}
		
		if (StringUtils.isNotBlank(dto.getAtUidCreate())) {
			searchStatement.append(getOperator(wherePrinted) + "o.atUidCreate LIKE :atUidCreate");
			wherePrinted = true;
		}
		
		Optional<CkMstRemarkType> opCkMstRemarkType = Optional.ofNullable(dto.getTCkMstRemarkType());
		if (opCkMstRemarkType.isPresent() && StringUtils.isNotBlank(opCkMstRemarkType.get().getRtId())) {
			searchStatement.append(getOperator(wherePrinted) + "o.TCkMstRemarkType.rtId = :rtId");
			wherePrinted = true;
		}
		if (opCkMstRemarkType.isPresent() && StringUtils.isNotBlank(opCkMstRemarkType.get().getRtDesc())) {
			searchStatement.append(getOperator(wherePrinted) + "o.TCkMstRemarkType.rtDesc LIKE :rtDesc");
			wherePrinted = true;
		}
		
		Optional<CoreAccn> opCoreAccn = Optional.ofNullable(dto.getTCoreAccn());
		if (opCoreAccn.isPresent() && StringUtils.isNotBlank(dto.getTCoreAccn().getAccnId())) {
			searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.accnId = :accnId");
			wherePrinted = true;
		}
		if (opCoreAccn.isPresent() && StringUtils.isNotBlank(dto.getTCoreAccn().getAccnName())) {
			searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.accnName LIKE :accnName");
			wherePrinted = true;
		}
		
		Optional<CkMstWorkflowType> opCkMstWorkflowType = Optional.ofNullable(dto.getTCkMstWorkflowType());
		if (opCkMstWorkflowType.isPresent() && StringUtils.isNotBlank(opCkMstWorkflowType.get().getWktId())) {
			searchStatement.append(getOperator(wherePrinted) + "o.TCkMstWorkflowType.wktId LIKE :wktId");
			wherePrinted = true;
		}
		if (opCkMstWorkflowType.isPresent() && StringUtils.isNotBlank(opCkMstWorkflowType.get().getWktDesc())) {
			searchStatement.append(getOperator(wherePrinted) + "o.TCkMstWorkflowType.wktDesc LIKE :wktDesc");
			wherePrinted = true;
		}
		
		return searchStatement.toString();
	}


	@Override
	protected HashMap<String, Object> getParameters(CkWorkflowRemark dto)
			throws ParameterException, ProcessingException {
		LOG.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (StringUtils.isNotBlank(dto.getArRemark())) {
				parameters.put("arRemark", "%" + dto.getArRemark() + "%");
			}
			
			if (null != dto.getAtDtCreate()) {
				parameters.put("atDtCreate", sdfDate.format(dto.getAtDtCreate()));
			}
			
			if (StringUtils.isNotBlank(dto.getAtUidCreate())) {
				parameters.put("atUidCreate", "%" + dto.getAtUidCreate() + "%");
			}
			
			Optional<CkMstRemarkType> opCkMstRemarkType = Optional.ofNullable(dto.getTCkMstRemarkType());
			if (opCkMstRemarkType.isPresent() && StringUtils.isNotBlank(opCkMstRemarkType.get().getRtId())) {
				parameters.put("rtId", opCkMstRemarkType.get().getRtId());
			}
			if (opCkMstRemarkType.isPresent() && StringUtils.isNotBlank(opCkMstRemarkType.get().getRtDesc())) {
				parameters.put("rtDesc", "%" + opCkMstRemarkType.get().getRtDesc() + "%");
			}
			Optional<CoreAccn> opCoreAccn = Optional.ofNullable(dto.getTCoreAccn());
			if (opCoreAccn.isPresent() && StringUtils.isNotBlank(dto.getTCoreAccn().getAccnId())) {
				parameters.put("accnId", opCoreAccn.get().getAccnId());
			}
			if (opCoreAccn.isPresent() && StringUtils.isNotBlank(dto.getTCoreAccn().getAccnName())) {
				parameters.put("accnName", "%" + opCoreAccn.get().getAccnName() + "%");
			}
			
			Optional<CkMstWorkflowType> opCkMstWorkflowType = Optional.ofNullable(dto.getTCkMstWorkflowType());
			if (opCkMstWorkflowType.isPresent() && StringUtils.isNotBlank(opCkMstWorkflowType.get().getWktId())) {
				parameters.put("wktId", opCkMstWorkflowType.get().getWktId());
			}
			if (opCkMstWorkflowType.isPresent() && StringUtils.isNotBlank(opCkMstWorkflowType.get().getWktDesc())) {
				parameters.put("wktDesc", "%" + opCkMstWorkflowType.get().getWktDesc() + "%");
			}

			return parameters;
		} catch (ParameterException ex) {
			LOG.error("getParameters", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("getParameters", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkWorkflowRemark whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		LOG.debug("whereDto");
		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			CkWorkflowRemark dto = new CkWorkflowRemark();
			CkMstRemarkType ckMstRemarkType = new CkMstRemarkType();
			CkMstWorkflowType ckMstWorkflowType = new CkMstWorkflowType();
			CoreAccn coreAccn = new CoreAccn();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;
				String attribute = entityWhere.getAttribute();
				if (attribute.equalsIgnoreCase("arRemark"))
					dto.setArRemark(opValue.get());
				else if (attribute.equalsIgnoreCase("atDtCreate"))
					dto.setAtDtCreate(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("atUidCreate"))
					dto.setAtUidCreate(opValue.get());
				else if (attribute.equalsIgnoreCase("TCkMstRemarkType.rtId"))
					ckMstRemarkType.setRtId(opValue.get());
				else if (attribute.equalsIgnoreCase("TCkMstRemarkType.rtDesc"))
					ckMstRemarkType.setRtDesc(opValue.get());
				else if (attribute.equalsIgnoreCase("TCoreAccn.accnId"))
					coreAccn.setAccnId(opValue.get());
				else if (attribute.equalsIgnoreCase("TCoreAccn.accnName"))
					coreAccn.setAccnName(opValue.get());
				else if (attribute.equalsIgnoreCase("TCkMstWorkflowType.wktId"))
					ckMstWorkflowType.setWktId(opValue.get());
				else if (attribute.equalsIgnoreCase("TCkMstWorkflowType.wktDesc"))
					ckMstWorkflowType.setWktDesc(opValue.get());
			}
			
			dto.setTCkMstWorkflowType(ckMstWorkflowType);
			dto.setTCkMstRemarkType(ckMstRemarkType);
			dto.setTCoreAccn(coreAccn);

			return dto;
		} catch (ParameterException ex) {
			LOG.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkWorkflowRemark paramD)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkWorkflowRemark setCoreMstLocale(CoreMstLocale paramCoreMstLocale, CkWorkflowRemark paramD)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
