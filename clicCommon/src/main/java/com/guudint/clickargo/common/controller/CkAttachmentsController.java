package com.guudint.clickargo.common.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.controller.AbstractCkController;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.dto.CkJobAttach;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.job.service.impl.CkJobAttachService;
import com.guudint.clickargo.manageaccn.dto.CkAccnAtt;
import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttType;
import com.guudint.clickargo.manageaccn.service.CkAccnAttService;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.master.dto.MstAttType;
import com.vcc.camelone.util.PrincipalUtilService;

@RequestMapping(value = "/api/v1/clickargo/attachments")
@CrossOrigin
public class CkAttachmentsController extends AbstractCkController {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkAttachmentsController.class);

	@Autowired
	private CkJobAttachService ckJobAttchService;
	
	@Autowired
	private PrincipalUtilService principalUtilService;
	
	@Autowired
	protected IEntityService<TCkJob, String, CkJob> ckJobService;
	
	@Autowired
	private CkAccnAttService ckAccnAttService;
	
	@Autowired
	private GenericDao<TCoreAccn, String> coreAccnDao;
	
	// Interface Methods
	/////////////////////
	@GetMapping("{entity}")
	public ResponseEntity<Object> newEntity(@PathVariable String entity){
		return super.newEntity(entity);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#createEntity(java.lang.String,
	 *      java.lang.String)
	 */
	@RequestMapping(value = "/{entity}", method = RequestMethod.POST)
	public ResponseEntity<Object> createEntity(@PathVariable String entity, @RequestBody String object) {
		log.debug("createEntity");
		return super.createEntity(entity, object);
		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntityById(java.lang.String,
	 *      java.lang.String)
	 */
	@RequestMapping(value = "{entity}/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntityById(@PathVariable String entity, @PathVariable String id) {
		log.debug("getEntityById");
		return super.getEntityById(entity, id);
		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#updateEntity(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = "{entity}/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateEntity(@RequestBody String object, @PathVariable String entity,
			@PathVariable String id) {
		log.debug("updateEntity");
		return super.updateEntity(object, entity, id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#deleteEntityById(java.lang.String,
	 *      java.lang.String)
	 */
	@RequestMapping(value = "{entity}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteEntityById(@PathVariable String entity, @PathVariable String id) {
		log.debug("deleteEntityById");
		
		try {
			if (entity.equalsIgnoreCase("accnAtt")) {
				return ResponseEntity.ok(ckAccnAttService.deleteAttachment(id, getPrincipal()));
			} else {
				return super.deleteEntityById(entity, id);
			}
		} catch (Exception e) {
            log.error("deleteEntityById", e);
            ServiceStatus serviceStatus = new ServiceStatus();
			serviceStatus.setStatus(ServiceStatus.STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, e.getMessage()));
			return new ResponseEntity<>(serviceStatus, HttpStatus.BAD_REQUEST);
        }
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.controller.entity.IEntityServiceController#getEntitiesBy(java.lang.String,
	 *      java.util.Map)
	 */
	@RequestMapping(value = "/{entity}/list", method = RequestMethod.GET)
	public ResponseEntity<Object> getEntitiesBy(@PathVariable String entity, @RequestParam Map<String, String> params) {
		log.debug("getEntitiesBy");
		return super.getEntitiesBy(entity, params);
	}
	
	/**
	 * 
	 * @param ckJobAttach
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ProcessingException
	 */
	@PostMapping(value = "/attach")
	public ResponseEntity<Object> createAttach(@RequestBody CkJobAttach ckJobAttach) throws ParameterException, EntityNotFoundException, ProcessingException {

		CkJob ckJob = ckJobService.findById(ckJobAttach.getTCkJob().getJobId());
		MstAttType attType = new MstAttType();
		attType.setMattId(ckJobAttach.getTMstAttType().getMattId());
		ckJobAttach.setAttId(CkUtil.generateId(ICkConstant.PREFIX_JOB_ATT));
		ckJobAttach.setTCkJob(ckJob);
		ckJobAttach.setTMstAttType(attType);
		
		ckJobAttach.setAttStatus(RecordStatus.ACTIVE.getCode());
		ckJobAttach.setAttDtCreate(Calendar.getInstance().getTime());
		ckJobAttach.setAttUidCreate(principalUtilService.getPrincipal().getUserId());
		ckJobAttach.setAttDtLupd(Calendar.getInstance().getTime());
		ckJobAttach.setAttUidLupd(principalUtilService.getPrincipal().getUserId());
		
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			ckJobAttach = ckJobAttchService.createAttachment(ckJobAttach, principalUtilService.getPrincipal());
			return ResponseEntity.ok(ckJobAttach);
		} catch (Exception ex) {
			log.error("createAttach", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This method creates an account attachment
	 * @param ckAccnAtt
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/accnAttach")
	public ResponseEntity<Object> createAccnAtt(@RequestBody CkAccnAtt ckAccnAtt)
			throws Exception {

		TCoreAccn tcoreAccn= coreAccnDao.find(ckAccnAtt.getTCoreAccn().getAccnId());
		CoreAccn coreAccn = new CoreAccn(tcoreAccn);
		CkMstAccnAttType mstAccnAttType = new CkMstAccnAttType();
		mstAccnAttType.setId(ckAccnAtt.getTCkMstAccnAttType().getId());
		ckAccnAtt.setAatId(CkUtil.generateId(ICkConstant.PREFIX_ACC_ATT));
		ckAccnAtt.setTCoreAccn(coreAccn);
		ckAccnAtt.setTCkMstAccnAttType(mstAccnAttType);
		
		ckAccnAtt.setAtStatus(RecordStatus.ACTIVE.getCode());
		ckAccnAtt.setAtDtCreate(Calendar.getInstance().getTime());
		ckAccnAtt.setAtUidCreate(principalUtilService.getPrincipal().getUserId());
		ckAccnAtt.setAtDtLupd(Calendar.getInstance().getTime());
		ckAccnAtt.setAtUidLupd(principalUtilService.getPrincipal().getUserId());
		
		// Set validity time to end of day.
		Calendar cal = DateUtils.toCalendar(ckAccnAtt.getAtDtValidility());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date validityDate = cal.getTime();
		ckAccnAtt.setAtDtValidility(validityDate);  
		
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			ckAccnAtt = ckAccnAttService.createAttachment(ckAccnAtt, principalUtilService.getPrincipal());
			return ResponseEntity.ok(ckAccnAtt);
		} catch (Exception ex) {
			log.error("createAccnAtt", ex);
			serviceStatus.setStatus(STATUS.EXCEPTION);
			serviceStatus.setErr(new ServiceError(-100, ex));
			return new ResponseEntity<Object>(serviceStatus, HttpStatus.BAD_REQUEST);
		}
	}
	

}
