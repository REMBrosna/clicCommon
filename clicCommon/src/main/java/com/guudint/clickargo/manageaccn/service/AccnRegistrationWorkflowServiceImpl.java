package com.guudint.clickargo.manageaccn.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.model.TCkSvcWorkflow;
import com.guudint.clickargo.clicservice.service.AbstractWorkflowService;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstAccnType;

@Service("AccnRegistrationWorkflowServiceImpl")
public class AccnRegistrationWorkflowServiceImpl extends AbstractWorkflowService<TCoreAccn, CoreAccn> {

	private static final Logger LOG = Logger.getLogger(AccnRegistrationWorkflowServiceImpl.class);

	@Autowired
	@Qualifier("coreAccDao")
	private GenericDao<TCoreAccn, String> coreAccDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CoreAccn moveState(FormActions action, CoreAccn dto, Principal principal, ServiceTypes serviceTypes)
			throws ParameterException, ProcessingException, Exception {
		try {
			if (null == action)
				throw new ParameterException("param action null");
			if (null == dto)
				throw new ParameterException("param dto null");
			if (null == principal)
				throw new ParameterException("param principal null");
			if (null == serviceTypes)
				throw new ParameterException("param serviceTypes null");

			TCoreAccn entity = coreAccDao.find(dto.getAccnId());
			if (entity == null)
				throw new EntityNotFoundException("account " + dto.getAccnId() + "not found");

			Hibernate.initialize(entity.getTMstAccnType());

			Optional<MstAccnType> opAccnType = Optional.ofNullable(principal.getCoreAccn().getTMstAccnType());
			if (!opAccnType.isPresent())
				throw new ProcessingException("account type null");

			Optional<List<String>> opAuthRoles = Optional.ofNullable(principal.getRoleList());
			if (!opAuthRoles.isPresent())
				throw new ProcessingException("principal roles null or empty");

			Optional<String> opAppsCode = Optional.ofNullable(principal.getAppsCode());
			if (!opAppsCode.isPresent())
				throw new ProcessingException("principal appscode null or empty");

			StringBuilder str = new StringBuilder("from TCkSvcWorkflow o where o.wkflStatus=:wkflStatus");
			str.append(" and o.TCkMstFormAction.fmactId=:action");
			str.append(" and o.TCkMstJobStateByWkflFromState.jbstId=:fromState");
			str.append(" and o.TCkMstServiceType.svctId=:serviceType");
			str.append(" and o.TCoreApps.appsCode=:appsCode");
			str.append(" and o.TMstAccnType.atypId=:accnType");
			str.append(" and o.TCoreRole.id.roleId in (:roles)");
			str.append(" and o.wkflId LIKE 'ACNREG%'");

			Map<String, Object> params = new HashMap<>();
			params.put("wkflStatus", RecordStatus.ACTIVE.getCode());
			params.put("action", action.name());

			Character status = entity.getAccnStatus();

			params.put("fromState", CoreAccnStateEnum.getAltCodeByState(status));
			params.put("serviceType", serviceTypes.getId());
			params.put("appsCode", opAppsCode.get());
			params.put("accnType", opAccnType.get().getAtypId());
			params.put("roles", opAuthRoles.get());

			List<TCkSvcWorkflow> listWfState = ckSvcWorkflowDao.getByQuery(str.toString(), params);
			if (listWfState == null || listWfState.size() <= 0 || listWfState.isEmpty())
				throw new ProcessingException("no state machine configured for " + entity.getAccnStatus()
						+ " with action [" + action.name() + "] for this principal");

			TCkSvcWorkflow toStateWf = listWfState.get(0);
			char newState = CoreAccnStateEnum
					.getStateByAltCodeAndDesc(toStateWf.getTCkMstJobStateByWkflToState().getJbstId(), "REG");
			entity.setAccnStatus(newState);
			entity.setAccnDtLupd(new Date());
			entity.setAccnUidLupd(principal.getUserId());
			coreAccDao.update(entity);

			LOG.info("moveState " + status + " -> " + toStateWf.getTCkMstJobStateByWkflToState().getJbstId());

			String event = "";
			switch (action) {
			case SUBMIT:
				event = "ACCOUNT REGISTRATION SUBMITTED";
				break;
			case APPROVE:
				event = "ACCOUNT REGISTRATION APPROVED";
				break;
			case DELETE:
				event = "ACCOUNT REGISTRATION DELETED";
				break;
			case REJECT:
				event = "ACCOUNT REGISTRATION REJECTED";
				break;
			default:
				break;

			}
			dto.setAccnStatus(newState);
			audit(dto.getAccnId(), principal, action, event);
			return dto;

		} catch (Exception e) {
			LOG.error("moveState");
			throw e;
		}
	}

}
