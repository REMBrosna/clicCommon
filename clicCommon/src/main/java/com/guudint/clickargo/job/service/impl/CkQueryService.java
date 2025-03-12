package com.guudint.clickargo.job.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.dto.CkJobQuery;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.job.model.TCkJobQuery;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Service
public class CkQueryService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkQueryService.class);

	@Autowired
	@Qualifier("ckJobQueryDao")
	private GenericDao<TCkJobQuery, String> ckJobQueryDao;

	@Autowired
	@Qualifier("ckJobDao")
	private GenericDao<TCkJob, String> ckJobDao;

	@Autowired
	private CkJobQueryService ckJobQueryService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkJobQuery> fetchQuery(String jobId, Principal principal) throws Exception {

		try {

			if (StringUtils.isBlank(jobId))
				new ParameterException("param jobId null or empty");
			if (null == principal)
				new ParameterException("param principal null");

			Map<String, Object> criterias = new HashMap<>();
			criterias.put("TCkJob.jobId", jobId);
			criterias.put("qryStatus", RecordStatus.ACTIVE.getCode());

			List<String> fieldsDesc = new ArrayList<>();
			fieldsDesc.add("qryDtCreate");

			List<TCkJobQuery> tQueryList = ckJobQueryDao.getByCriteria(criterias, null, fieldsDesc, 0, 0);

			List<CkJobQuery> ckList = tQueryList.stream().map(rmk -> {
				try {
					return ckJobQueryService.dtoFromEntity(rmk);
				} catch (ParameterException | ProcessingException e) {
					log.error("fetchQuery", e);
				}
				return null;
			}).collect(Collectors.toList());

			return ckList;
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
	}

	/**
	 * Reply Query
	 *
	 * @param qurId
	 * @param replyMessage
	 * @param principal
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void replyQuery(String qurId, String replyMessage, Principal principal) throws Exception {

		try {
			TCkJobQuery ckQuery = ckJobQueryDao.find(qurId);
			ckQuery.setQryResponse(replyMessage);
			ckQuery.setQryDtResponse(new Date());

			TCoreUsr usrResponder = new TCoreUsr();
			usrResponder.setUsrUid(principal.getUserId());
			ckQuery.setTCoreUsrByQryResponder(usrResponder);

			ckJobQueryDao.update(ckQuery);

			// TODO Send email notification

		} catch (Exception e) {
			log.error("replyQuery", e);
			throw e;
		}
	}

	/**
	 * Reply Query
	 *
	 * @param qurId
	 * @param replyMessage
	 * @param principal
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void createQuery(CkJobQuery query, Principal principal) throws Exception {

		try {

			Optional<CkJob> opJob = Optional.ofNullable(query.getTCkJob());
			if (opJob.isPresent()) {
				TCkJob job = ckJobDao.find(opJob.get().getJobId());

				if (job == null)
					throw new EntityNotFoundException("job not found");

				Date now = new Date();
				TCkJobQuery ckQuery = new TCkJobQuery();
				ckQuery.setQryId(CkUtil.generateIdSynch(ICkConstant.PREFIX_JOB_QUERY));
				ckQuery.setQryQuery(query.getQryQuery());
				ckQuery.setQryDtQuery(now);
				ckQuery.setQryDtCreate(now);
				ckQuery.setQryDtLupd(now);
				ckQuery.setTCkJob(job);
				ckQuery.setQryStatus(RecordStatus.ACTIVE.getCode());

				ckQuery.setQryUidLupd(principal.getUserId());
				ckQuery.setQryUidCreate(principal.getUserId());

				TCoreUsr usrRequester = new TCoreUsr();
				usrRequester.setUsrUid(principal.getUserId());
				ckQuery.setTCoreUsrByQryRequester(usrRequester);

				ckJobQueryDao.add(ckQuery);

			}

			// TODO Send email notification

		} catch (Exception e) {
			log.error("replyQuery", e);
			throw e;
		}
	}

	/**
	 * Remove Query
	 *
	 * @param qurId
	 * @param replyMessage
	 * @param principal
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void removeQuery(String qurId, Principal principal) throws Exception {

		try {
			TCkJobQuery ckQuery = ckJobQueryDao.find(qurId);
			ckQuery.setQryDtLupd(new Date());
			ckQuery.setQryUidLupd(principal.getUserId());
			ckQuery.setQryStatus(RecordStatus.INACTIVE.getCode());

			ckJobQueryDao.update(ckQuery);

			// TODO Send email notification

		} catch (Exception e) {
			log.error("removeQuery", e);
			throw e;
		}
	}
	
	/**
	 * Return one query
	 * @param qurId
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobQuery getQuery(String qurId, Principal principal) throws Exception {

		try {
			TCkJobQuery ckQuery = ckJobQueryDao.find(qurId);
			return ckJobQueryService.dtoFromEntity(ckQuery);
		} catch (Exception e) {
			log.error("getQuery", e);
			throw e;
		}
	}

}
