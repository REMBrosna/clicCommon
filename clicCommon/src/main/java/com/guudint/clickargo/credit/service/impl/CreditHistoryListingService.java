package com.guudint.clickargo.credit.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.service.AbstractCkListingService;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.guudint.clickargo.journal.model.TCkCreditJournal;
import com.guudint.clickargo.master.dto.CkMstJournalTxnType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstJournalTxnType;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstCurrency;

@Service
public class CreditHistoryListingService extends AbstractCkListingService<TCkCreditJournal, String, CkCreditJournal> {

	private static Logger LOG = Logger.getLogger(CreditHistoryListingService.class);
	private static String AUDIT_TAG = "CK CREDIT JOURNAL";
	private static String TABLE_NAME = "T_CK_CREDIT_JOURNAL";

	public CreditHistoryListingService() {
		super("ckCreditJournalDao", AUDIT_TAG, TCkCreditJournal.class.getName(), TABLE_NAME);
	}

	@Override
	public List<CkCreditJournal> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkCreditJournal dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));
			String selectClause = "FROM TCkCreditJournal o ";
			String orderByClauses = formatOrderByObj(filterRequest.getOrderBy()).toString();
			List<TCkCreditJournal> entities = this.findEntitiesByAnd(dto, selectClause, orderByClauses,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkCreditJournal> dtos = entities.stream().map(x -> {
				try {
					return dtoFromEntity(x);
				} catch (ParameterException | ProcessingException e) {
					LOG.error("filterBy", e);
				}
				return null;
			}).collect(Collectors.toList());

			return dtos;
		} catch (ParameterException | ProcessingException ex) {
			LOG.error("filterBy", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("filterBy", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkCreditJournal initEnity(TCkCreditJournal entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkMstServiceType());
			Hibernate.initialize(entity.getTCoreAccn());
			Hibernate.initialize(entity.getTCkMstJournalTxnType());
		}

		return entity;
	}

	@Override
	protected CkCreditJournal dtoFromEntity(TCkCreditJournal entity) throws ParameterException, ProcessingException {
		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkCreditJournal dto = new CkCreditJournal(entity);

			TCkMstServiceType serviceTypeE = entity.getTCkMstServiceType();
			if (serviceTypeE != null)
				dto.setTCkMstServiceType(new CkMstServiceType(serviceTypeE));

			TCoreAccn accnE = entity.getTCoreAccn();
			if (accnE != null)
				dto.setTCoreAccn(new CoreAccn(accnE));

			TCkMstJournalTxnType journalTxnTypeE = entity.getTCkMstJournalTxnType();
			if (journalTxnTypeE != null)
				dto.setTCkMstJournalTxnType(new CkMstJournalTxnType(journalTxnTypeE));

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
	protected String getWhereClause(CkCreditJournal dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");
		try {
			if (null == dto)
				throw new ParameterException("param dto null");
			StringBuffer searchStatement = new StringBuffer();

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");

			// filter by account
			CoreAccn accn = principal.getCoreAccn();
			searchStatement.append(getOperator(wherePrinted)).append("o.TCoreAccn.accnId = :accnId");
			wherePrinted = true;

			searchStatement.append(getOperator(wherePrinted)).append("o.cjnStatus = :recStatus");
			wherePrinted = true;

			// for filter
			Optional<CoreAccn> opAccn = Optional.ofNullable(dto.getTCoreAccn());
			if (opAccn.isPresent()) {
				if (StringUtils.isNotBlank(accn.getAccnName())) {
					searchStatement.append(getOperator(wherePrinted)).append("o.TCoreAccn.accnName LIKE :accnName");
					wherePrinted = true;
				}
			}

			Optional<CkMstServiceType> opServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
			if (opServiceType.isPresent()) {
				if (StringUtils.isNotBlank(opServiceType.get().getSvctId())) {
					searchStatement.append(getOperator(wherePrinted))
							.append("o.TCkMstServiceType.svctId = :serviceTypeId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opServiceType.get().getSvctName())) {
					searchStatement.append(getOperator(wherePrinted))
							.append("o.TCkMstServiceType.svctName LIKE :serviceTypeName");
					wherePrinted = true;
				}
			}

			Optional<CkMstJournalTxnType> opJournalTxnType = Optional.ofNullable(dto.getTCkMstJournalTxnType());
			if (opJournalTxnType.isPresent()) {
				if (StringUtils.isNotBlank(opJournalTxnType.get().getJttId())) {
					searchStatement.append(getOperator(wherePrinted))
							.append("o.TCkMstJournalTxnType.jttId = :journalTxnTypeId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opJournalTxnType.get().getJttName())) {
					searchStatement.append(getOperator(wherePrinted))
							.append("o.TCkMstJournalTxnType.jttName LIKE :journalTxnTypeName");
					wherePrinted = true;
				}
			}

			Optional<MstCurrency> opCurrency = Optional.ofNullable(dto.getTMstCurrency());
			if (opCurrency.isPresent()) {
				if (StringUtils.isNotBlank(opCurrency.get().getCcyCode())) {
					searchStatement.append(getOperator(wherePrinted)).append("o.TMstCurrency.ccyCode = :currencyCode");
					wherePrinted = true;
				}
			}

			if (dto.getCjnDtCreate() != null) {
				searchStatement.append(getOperator(wherePrinted))
						.append("DATE_FORMAT(o.cjnDtCreate,'%d/%m/%Y') = :txnDate");
				wherePrinted = true;
			}

			return searchStatement.toString();
		} catch (ParameterException ex) {
			LOG.error("getWhereClause", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("getWhereClause", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected HashMap<String, Object> getParameters(CkCreditJournal dto)
			throws ParameterException, ProcessingException {
		LOG.debug("getParameters");

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			Principal principal = principalUtilService.getPrincipal();

			if (principal == null)
				throw new ProcessingException("principal is null");
			CoreAccn accn = principal.getCoreAccn();
			parameters.put("accnId", accn.getAccnId());
			parameters.put("recStatus", RecordStatus.ACTIVE.getCode());

			// for filter
			Optional<CoreAccn> opAccn = Optional.ofNullable(dto.getTCoreAccn());
			if (opAccn.isPresent()) {
				if (StringUtils.isNotBlank(accn.getAccnName()))
					parameters.put("accnName", "%" + accn.getAccnName() + "%");
			}

			Optional<CkMstServiceType> opServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
			if (opServiceType.isPresent()) {
				if (StringUtils.isNotBlank(opServiceType.get().getSvctId()))
					parameters.put("serviceTypeId", opServiceType.get().getSvctId());

				if (StringUtils.isNotBlank(opServiceType.get().getSvctName()))
					parameters.put("serviceTypeName", "%" + opServiceType.get().getSvctName() + "%");
			}

			Optional<CkMstJournalTxnType> opJournalTxnType = Optional.ofNullable(dto.getTCkMstJournalTxnType());
			if (opJournalTxnType.isPresent()) {
				if (StringUtils.isNotBlank(opJournalTxnType.get().getJttId()))
					parameters.put("journalTxnTypeId", opJournalTxnType.get().getJttId());

				if (StringUtils.isNotBlank(opJournalTxnType.get().getJttName()))
					parameters.put("journalTxnTypeName", "%" + opJournalTxnType.get().getJttName() + "%");
			}

			Optional<MstCurrency> opCurrency = Optional.ofNullable(dto.getTMstCurrency());
			if (opCurrency.isPresent()) {
				if (StringUtils.isNotBlank(opCurrency.get().getCcyCode()))
					parameters.put("currencyCode", opCurrency.get().getCcyCode());
			}

			if (dto.getCjnDtCreate() != null) {
				parameters.put("txnDate", sdfDate.format(dto.getCjnDtCreate()));
			}

			return parameters;
		} catch (

		ParameterException ex) {
			LOG.error("getParameters", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("getParameters", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkCreditJournal whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			CkCreditJournal dto = new CkCreditJournal();
			CkMstServiceType serviceType = new CkMstServiceType();
			CoreAccn accn = new CoreAccn();
			MstCurrency curr = new MstCurrency();
			CkMstJournalTxnType journalTxnType = new CkMstJournalTxnType();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstServiceType.svctId"))
					serviceType.setSvctId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstServiceType.svctName"))
					serviceType.setSvctName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnId"))
					accn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnName"))
					accn.setAccnName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TMstCurrency.ccyCode"))
					curr.setCcyCode(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstJournalTxnType.jttId"))
					journalTxnType.setJttId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("cjnDtCreate"))
					dto.setCjnDtCreate(sdfDate.parse(opValue.get()));
			}

			dto.setTCkMstServiceType(serviceType);
			dto.setTCoreAccn(accn);
			dto.setTCkMstJournalTxnType(journalTxnType);
			dto.setTMstCurrency(curr);
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
	protected String formatOrderBy(String attribute) throws Exception {
		String newAttr = attribute;
		if (StringUtils.contains(newAttr, "tcoreAccn"))
			newAttr = newAttr.replace("tcoreAccn", "TCoreAccn");

		if (StringUtils.contains(newAttr, "tckMstServiceType"))
			newAttr = newAttr.replace("tckMstServiceType", "TCkMstServiceType");

		if (StringUtils.contains(newAttr, "tckJob"))
			newAttr = newAttr.replace("tckJob", "TCkJob");

		if (StringUtils.contains(newAttr, "tckMstJournalTxnType"))
			newAttr = newAttr.replace("tckMstJournalTxnType", "TCkMstJournalTxnType");

		return newAttr;
	}

}
