package com.guudint.clickargo.common.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkFileUtil;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dao.CkWhitelabelDao;
import com.guudint.clickargo.common.dto.CkCtWhitelabel;
import com.guudint.clickargo.common.model.TCkWhitelabel;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;

@Service
public class CkWhitelabelService {

	@Autowired
	private CkWhitelabelDao whiteLabelDao;

	@Autowired
	protected CkFileUtil ckFileUtil;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getBackgroundImage(String name) throws Exception {

		TCkWhitelabel wl = whiteLabelDao.findByName(name);

		if (wl == null)
			throw new Exception("No record found for custom login");

		if (StringUtils.isBlank(wl.getWlImgLoc())) {
			return null;
		}

		byte[] bytes = Files.readAllBytes(Paths.get(wl.getWlImgLoc()));
		String base64Str = Base64.getEncoder().encodeToString(bytes);

		if (StringUtils.isBlank(base64Str)) {
			throw new Exception("Background image not set.");
		}

		return base64Str;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public CkCtWhitelabel getDetails(String accnId) throws Exception {
		TCkWhitelabel wl = whiteLabelDao.findByAccnId(accnId);

		if (wl == null)
			throw new Exception("No record found for custom login");

		CkCtWhitelabel dto = new CkCtWhitelabel(wl);
		Hibernate.initialize(dto.getTCoreAccn());
		dto.setTCoreAccn(new CoreAccn(wl.getTCoreAccn()));

		if (StringUtils.isNotBlank(wl.getWlImgLoc())) {
			dto.setFilename(
					wl.getWlImgLoc().substring(wl.getWlImgLoc().lastIndexOf('/') + 1, wl.getWlImgLoc().length()));
		}

		return dto;
	}

	public CkCtWhitelabel updateDetails(CkCtWhitelabel dto, Principal principal) throws Exception {
		if (dto == null)
			throw new ParameterException("dto null");

		TCkWhitelabel entity = new TCkWhitelabel();

		if (StringUtils.isBlank(dto.getWlId())) {
			dto.setWlId(CkUtil.generateId());
			// set to accnId for now
			dto.setWlName(dto.getTCoreAccn().getAccnId());
			entity = dto.toEntity(entity);
			entity.setWlId(CkUtil.generateId());
			entity.setTCoreAccn(dto.getTCoreAccn().toEntity(new TCoreAccn()));
			entity.setWlDtLupd(new Date());
			entity.setWlUidLupd(principal.getUserId());
			entity.setWlDtCreate(new Date());
			entity.setWtUidCreate(principal.getUserId());
			entity.setWlStatus(RecordStatus.ACTIVE.getCode());
		} else {
			//means it is existing, get the details from backend and update
			entity = whiteLabelDao.find(dto.getWlId());
			if(entity == null)
				throw new EntityNotFoundException("record " + dto.getWlId() + " not found");
			entity.setWlDtLupd(new Date());
			entity.setWlUidLupd(principal.getUserId());
			
		}

		//Only save if there is value
		if (dto.getData() != null) {
			String filePath = ckFileUtil.saveAttachment(dto.getWlName(), dto.getFilename(), dto.getData());
			entity.setWlImgLoc(filePath);
		}
		
		whiteLabelDao.saveOrUpdate(entity);
		dto.fromEntity(entity);
		return dto;

	}

}
