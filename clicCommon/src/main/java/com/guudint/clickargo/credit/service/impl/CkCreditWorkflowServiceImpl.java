package com.guudint.clickargo.credit.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.dao.CkSvcWorkflowDao;
import com.guudint.clickargo.clicservice.model.TCkSvcWorkflow;
import com.guudint.clickargo.clicservice.service.AbstractWorkflowService;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.credit.dao.CkCreditRequestDao;
import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.guudint.clickargo.credit.dto.CkMstCreditRequestState;
import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstAccnType;

/**
 * 
 */
@Service("creditRequestWorkflowService")
public class CkCreditWorkflowServiceImpl extends AbstractWorkflowService<TCkCreditRequest, CkCreditRequest> {

	@Autowired
	private CkSvcWorkflowDao ckSvcWorkflowDao;
	@Autowired
	private CkCreditRequestDao ckCreditRequestDao;

	@Override
	@Transactional
	public CkCreditRequest moveState(FormActions action, CkCreditRequest dto, Principal principal,
			ServiceTypes serviceTypes) throws ParameterException, ProcessingException, Exception {
		if (action == null)
			throw new ParameterException("param action null");
		Optional<MstAccnType> opAccnType = Optional.ofNullable(principal.getCoreAccn().getTMstAccnType());
		if (!opAccnType.isPresent())
			throw new ProcessingException("account type null");
		if (principal.getRoleList() == null)
			throw new ProcessingException("principal roles null or empty");
		if (principal.getRoleList().isEmpty())
			throw new ProcessingException("principal roles null or empty");
		Optional<String> opAppsCode = Optional.ofNullable(principal.getAppsCode());
		if (!opAppsCode.isPresent())
			throw new ProcessingException("principal appscode null or empty");

		TCkCreditRequest tCkCreditRequest = ckCreditRequestDao.find(dto.getCruId());
		if (tCkCreditRequest == null) {
			throw new EntityNotFoundException("Credit Update not found");
		}
		if (action.name().equals(FormActions.NEW.name())) {
			return dto;
		}
		Optional<TCkSvcWorkflow> tCkWorkFlowOpt = ckSvcWorkflowDao.findToState(action.name(),
				tCkCreditRequest.getTCkMstCreditRequestState().getStId(), dto.getTCkMstServiceType().getSvctId(),
				opAppsCode.get(), opAccnType.get().getAtypId(), principal.getRoleList(), "CRU");
		
		if (tCkWorkFlowOpt.isPresent()) {
			CkMstCreditRequestState state = new CkMstCreditRequestState();
			state.setStId(tCkWorkFlowOpt.get().getTCkMstJobStateByWkflToState().getJbstId());
			
			dto.setTCkMstCreditRequestState(state);
			
			Date now = new Date();
			String auditEvent = "";
			switch (action) {
				case SUBMIT:
					dto.setCruDtSubmitted(now);
					dto.setCruUidSubmitted(principal.getUserId());
					auditEvent = "CREDIT LIMIT UPDATE SUBMITTED";
					break;
				case APPROVE:
					dto.setCruDtApprove(now);
					dto.setCruUsrApprove(principal.getUserId());
					auditEvent = "CREDIT LIMIT UPDATE APPROVED";
					break;
				case REJECT:
					dto.setCruDtReject(now);
					dto.setCruUsrReject(principal.getUserId());
					auditEvent = "CREDIT LIMIT UPDATE REJECTED";
				case DELETE:
					dto.setCruStatus(RecordStatus.INACTIVE.getCode());
					auditEvent = "CREDIT LIMIT UPDATE DELETED";
				default:
					break;
			}
			audit(tCkCreditRequest.getCruId(), principal, action, auditEvent);
			return dto;
		} else {
			throw new ProcessingException(
					String.format("no state machine configured for %s with action [%s] for this principal",
							tCkCreditRequest.getTCkMstCreditRequestState().getStId(), action.name()));
		}
	}
}
