package com.guudint.clickargo.common.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkFormControlDto;
import com.guudint.clickargo.common.dto.CkFormControlReqDto;
import com.guudint.clickargo.common.model.TCkFormControl;
import com.guudint.clickargo.common.service.ICkFormControlService;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.master.dto.MstAccnType;

@Service
public class CkFormControlServiceImpl implements ICkFormControlService {

	@Autowired
	@Qualifier("ckFormControlDao")
	private GenericDao<TCkFormControl, String> ckFormControlDao;
	
	@Autowired
	@Qualifier("ccmAccnService")
	private IEntityService<TCoreAccn, String, CoreAccn> accnService;

	@Override
	public List<CkFormControlDto> getControls(CkFormControlReqDto reqDto, Principal principal)
			throws ParameterException, ProcessingException, Exception {
		List<CkFormControlDto> list = new ArrayList<CkFormControlDto>();
		try {

			if (null == principal)
				throw new ParameterException("param principal null");
			if (null == reqDto)
				throw new ParameterException("param reqDto null");

			DetachedCriteria mainCriteria = DetachedCriteria.forClass(TCkFormControl.class);
			mainCriteria.add(Restrictions.eq("ctrlStatus", RecordStatus.ACTIVE.getCode()));
			mainCriteria.add(Restrictions.eq("TCkMstEntityType.entyId", reqDto.getEntityType().name()));

			if (null != principal.getCoreAccn()) {
				Optional<MstAccnType> opMstAccType = Optional.ofNullable(principal.getCoreAccn().getTMstAccnType());
				if (opMstAccType.isPresent())
					mainCriteria.add(Restrictions.eq("TMstAccnType.atypId", opMstAccType.get().getAtypId()));
			}

			Junction conditions = Restrictions.disjunction();
			if (null != principal.getRoleList()) {
				principal.getRoleList().forEach(role -> {
					conditions.add(Restrictions.conjunction().add(Restrictions.eq("ctrlUsrRole", role)));
				});
			}

			mainCriteria.add(conditions);
			mainCriteria.add(Restrictions.eq("TCkMstEntityState.enstId", reqDto.getEntityState().name()));
			mainCriteria.add(Restrictions.eq("TCkMstFormType.fmtypId", reqDto.getPage().name()));
			
			//added apps code
			mainCriteria.add(Restrictions.eq("TCoreApps.appsCode", principal.getAppsCode()));

			List<TCkFormControl> entityList = ckFormControlDao.getByCriteria(mainCriteria);

			entityList.forEach(e -> {
				list.add(dtoFromEntity(e));
			});

		} catch (Exception ex) {
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<CkFormControlDto> getControls(CkFormControlReqDto reqDto, String accnId, String roles)
			throws ParameterException, ProcessingException, Exception {
		List<CkFormControlDto> list = new ArrayList<CkFormControlDto>();
		try {

			if (StringUtils.isBlank(accnId))
				throw new ParameterException("param accnId null or empty");
			if (null == reqDto)
				throw new ParameterException("param reqDto null");

			DetachedCriteria mainCriteria = DetachedCriteria.forClass(TCkFormControl.class);
			mainCriteria.add(Restrictions.eq("ctrlStatus", RecordStatus.ACTIVE.getCode()));
			mainCriteria.add(Restrictions.eq("TCkMstEntityType.entyId", reqDto.getEntityType().name()));

			CoreAccn accn = accnService.findById(accnId);
			if (null != accn) {
				Optional<MstAccnType> opMstAccType = Optional.ofNullable(accn.getTMstAccnType());
				if (opMstAccType.isPresent())
					mainCriteria.add(Restrictions.eq("TMstAccnType.atypId", opMstAccType.get().getAtypId()));
			}

			//Roles is expected to be in concatenated by :
			List<String> rolesList = Arrays.asList(roles.split(":"));
			Junction conditions = Restrictions.disjunction();
			if (null != rolesList) {
				rolesList.forEach(role -> {
					conditions.add(Restrictions.conjunction().add(Restrictions.eq("ctrlUsrRole", role)));
				});
			}

			mainCriteria.add(conditions);
			mainCriteria.add(Restrictions.eq("TCkMstEntityState.enstId", reqDto.getEntityState().name()));
			mainCriteria.add(Restrictions.eq("TCkMstFormType.fmtypId", reqDto.getPage().name()));

			List<TCkFormControl> entityList = ckFormControlDao.getByCriteria(mainCriteria);

			entityList.forEach(e -> {
				list.add(dtoFromEntity(e));
			});

		} catch (Exception ex) {
			throw ex;
		}
		return list;
	}

	private CkFormControlDto dtoFromEntity(TCkFormControl e) {
		CkFormControlDto ctrl = new CkFormControlDto();
		try {

			BeanUtils.copyProperties(ctrl, e);

			ctrl.setCtrlEntityState(
					null != e.getTCkMstEntityState() ? e.getTCkMstEntityState().getEnstId() : StringUtils.EMPTY);
			ctrl.setCtrlEntityType(
					null != e.getTCkMstEntityType() ? e.getTCkMstEntityType().getEntyId() : StringUtils.EMPTY);
			ctrl.setCtrlAction(
					null != e.getTCkMstFormAction() ? e.getTCkMstFormAction().getFmactId() : StringUtils.EMPTY);
			ctrl.setCtrlViewType(
					null != e.getTCkMstFormType() ? e.getTCkMstFormType().getFmtypId() : StringUtils.EMPTY);
			ctrl.setCtrlAccn(null != e.getTCoreAccn() ? e.getTCoreAccn().getAccnId() : StringUtils.EMPTY);
			ctrl.setCtrlAccnType(null != e.getTMstAccnType() ? e.getTMstAccnType().getAtypId() : StringUtils.EMPTY);
		} catch (Exception ex) {
			Log.error(ex);
		}

		return ctrl;
	}

}
