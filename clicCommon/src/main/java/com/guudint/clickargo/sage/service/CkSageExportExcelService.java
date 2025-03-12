package com.guudint.clickargo.sage.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkDateFormat;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.service.impl.CkNotificationUtilService;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.sage.dao.CkSageIntegrationDao;
import com.guudint.clickargo.sage.dto.CkCtMstSageIntStateEnum;
import com.guudint.clickargo.sage.dto.CkCtMstSageIntTypeEnum;
import com.guudint.clickargo.sage.dto.CkSageParamDto;
import com.guudint.clickargo.sage.model.TCkCtMstSageIntState;
import com.guudint.clickargo.sage.model.TCkCtMstSageIntType;
import com.guudint.clickargo.sage.model.TCkSageIntegration;
import com.vcc.camelone.can.device.NotificationParam;
import com.vcc.camelone.can.model.TCoreNotificationLog;
import com.vcc.camelone.can.service.impl.NotificationService;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.util.email.SysParam;

@Service
public abstract class CkSageExportExcelService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkSageExportExcelService.class);

	//@PersistenceContext
	//private EntityManager entityManager;
	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	protected SysParam sysParam;

	@Autowired
	protected CkSageIntegrationDao sageIntegrartionDao;
	@Autowired
	protected CkNotificationUtilService notificationUtilService;
	@Autowired
	protected NotificationService notificationService;

	public static SimpleDateFormat yyyyMMddSDF = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat jobApproveDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static SimpleDateFormat yyyy_MM_ddSDF = new SimpleDateFormat("yyyy-MM-dd");

	private CkSageParamDto paramDto;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCkSageIntegration exportSageExcel(CkSageParamDto paramDto) throws Exception {

		this.paramDto = paramDto;

		TCkSageIntegration sageIntegration = null;

		try {
			List<Object[]> dtoList = fetchRecords(paramDto);

			byte[] excelFilebody = this.generateExcelFile(dtoList);

			String fileName = this.getFileName(paramDto);
			log.info("fileName:" + fileName);
			log.info("excelFilebody:" + excelFilebody.length);

			// String dateFolderName = sdf.format(date);

			// file path

			String parentPath = sysParam.getValString("CLICTRUCK_TO_SAGE_PATH", "/home/vcc/sage300/toSage/");
			Files.createDirectories(Paths.get(parentPath));

			Files.write(Paths.get(parentPath + fileName), excelFilebody);
			File file = new File(parentPath + fileName);

			sageIntegration = this.createTCkSageIntegration(paramDto.getBeginDate(), paramDto.getEndDate(),
					dtoList.size(), file.getAbsolutePath());

			// save to storage;
			// Don't auto push to SFTP
			/*
			 * // push to SFTP server CLICTRUCK_SAGE_SFTP String sageSftp =
			 * sysParam.getValString("CLICTRUCK_SAGE_SFTP", null);
			 * 
			 * if (StringUtils.isNotBlank(sageSftp)) { SFTPConfig sftpConfig = (new
			 * ObjectMapper()).readValue(sageSftp, SFTPConfig.class);
			 * 
			 * SFTPUtil.store(sftpConfig, Arrays.asList(file)); }
			 */
			sageIntegration.setTCkCtMstSageIntState(new TCkCtMstSageIntState(CkCtMstSageIntStateEnum.SUBMITTED.name(), null));
			
		} catch (Exception e) {
			log.error("", e);
			sageIntegration.setTCkCtMstSageIntState(new TCkCtMstSageIntState(CkCtMstSageIntStateEnum.ERROR.name(), null));
			throw e;
		} finally {
			if (null != sageIntegration) {
				try {

					sageIntegration.setTCkCtMstSageIntType(new TCkCtMstSageIntType(paramDto.getSageIntType().name(), null));
					sageIntegration.setTCkMstServiceType(new TCkMstServiceType(paramDto.getServiceType().getId(), null));
					// sageIntegration.setSintDtExport(new Date());
					sageIntegrartionDao.saveOrUpdate(sageIntegration);
				} catch (Exception e) {
					log.error("", e);
					throw e;
				}
			}
		}
		return sageIntegration;
	}

	@Transactional
	public void sendEmailToFinance(String emailSubject, String emailBody) throws Exception {

		try {
			NotificationParam param = new NotificationParam();
			param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
			param.setTemplateId("CKT_NTL_0000");

			String recipientStr = sysParam.getValString("CLICTRUCK_FINANCE_EMAIL_RECEIVER", "Zhang.Ji@guud.company");

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":sp_details", emailBody);

			param.setContentFeilds(contentFields);

			param.setRecipients(new ArrayList<>(Arrays.asList(recipientStr.split(","))));

			HashMap<String, String> subjectFields = new HashMap<>();
			subjectFields.put(":subject", emailSubject);
			param.setSubjectFields(subjectFields);

			TCoreNotificationLog notifLog = notificationUtilService.saveNotificationLog(param.toJson(), null, false);
			log.info("TCoreNotificationLog id: " + notifLog.getNlogId());
			notificationService.sendNotificationsByLogId(notifLog.getNlogId());
			// notificationService.notifySyn(param);

		} catch (Exception e) {
			log.error("Fail to send email." + emailSubject, e);
			throw e;
		}
	}

	protected List<Object[]> fetchRecords(CkSageParamDto paramDto ) {

		SimpleDateFormat sdf = new SimpleDateFormat(CkDateFormat.Java.YYYY_MM_DD_HHMMSS);
		
		String sql = String.format("select * from %s where %s between '%s' and '%s'", paramDto.getTableName(),
				paramDto.getDateFieldName(), sdf.format(paramDto.getBeginDate()), sdf.format(paramDto.getEndDate()));
		
		log.info("sql: " + sql);

		List<Object[]> objArrayList = this.executeNativeQuery(sql);

		return objArrayList;
	}

	protected String getFileName(CkSageParamDto paramDto) {

		String bDate = yyyyMMddSDF.format(paramDto.getBeginDate());
		String eDate = yyyyMMddSDF.format(paramDto.getEndDate());

		return paramDto.getFieldNamePrefix() + "_" + bDate + "_" + eDate + ".xls";
	}

	protected byte[] generateExcelFile(List<Object[]> dtoList) throws IOException {

		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet(paramDto.getSheetName());

		this.setSheetFirstRow(sheet);

		int rowId = 1;
		for (Object[] objArray : dtoList) {

			int cellid = 0;
			HSSFRow row = sheet.createRow(rowId);
			rowId++;

			for (int i=0; i< objArray.length -1 ; i++) {
				// ignore last field, because last field is DATETIME_FIELD, use for filter data
				Object obj = objArray[i];
				HSSFCell cell = row.createCell(cellid++);
				if( null != obj) {
					cell.setCellValue(obj.toString());
				}
			}
		}
		// byte[] excelBody = wb.getBytes();
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		wb.write(outByteStream);
		byte[] excelBody = outByteStream.toByteArray();

		wb.close();

		return excelBody;
	}

	protected TCkSageIntegration createTCkSageIntegration(Date beginDate, Date endDate, int noRecords,
			String locExport) {

		TCkSageIntegration sageInte = new TCkSageIntegration();
		sageInte.setSintId(CkUtil.generateId(TCkSageIntegration.PREFIX_ID));

		sageInte.setSintDtStart(beginDate);
		sageInte.setSintDtEnd(endDate);
		sageInte.setSintNoRecords(noRecords);
		// sageInte.setSintNoSuccess(null);
		// sageInte.setSintNoFail(null);
		// sageInte.setSintDtExport(new Date());
		// sageInte.setSintDtImport(endDate);
		sageInte.setSintLocExport(locExport);
		// sageInte.setSintLocImport(null);

		sageInte.setSintStatus(Constant.ACTIVE_STATUS);
		sageInte.setSintUidCreate(Constant.ACCN_CREATE_SYS_USER);
		sageInte.setSintDtCreate(new Date());
		// sageInte.setSintDtLupd(endDate);
		// sageInte.setSintUidLupd(null);
		return sageInte;
	}

	@SuppressWarnings(value = { "unchecked" })
	public List<Object[]> executeNativeQuery(String sql) {
		
		return sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		
		//Query query = entityManager.createNativeQuery(sql);
		//return query.getResultList();
	}

	protected abstract void setSheetFirstRow(HSSFSheet sheet);
	
}
