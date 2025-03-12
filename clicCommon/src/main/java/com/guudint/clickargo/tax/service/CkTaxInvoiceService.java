package com.guudint.clickargo.tax.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import com.guudint.clickargo.common.CkFileUtil;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.tax.dao.CkTaxInvoiceDao;
import com.guudint.clickargo.tax.model.TCkTaxInvoice;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.config.model.TCoreSysparam;

@Service
public class CkTaxInvoiceService {
	
	private static Logger LOG = Logger.getLogger(CkTaxInvoiceService.class);

	@Autowired
	protected CkTaxInvoiceDao ckTaxInvoiceDao;
	
	@Autowired
	protected GenericDao<TCoreSysparam, String> coreSysparamDao;

	@Autowired
	CkFileUtil ckFileUtil;
	
	public String uploadTaxInvoice(MultipartFile file, Principal principal) throws Exception {
		String fileName = file.getOriginalFilename();
		String pattern = "\\d{15}-\\d{16}-\\d{15}-\\d{14}\\.\\w+";

		String fakturNumber = null;
		String jobId = null;
		String savePath = null;

		if (!Pattern.matches(pattern, fileName)) {
			throw new Exception("File name is incorrect!");
		}

		// Split by dash and file extension using regular expression
		String[] parts = fileName.split("-|\\.");

		// Extract individual parts
		// String part1 = parts[0]; // GLI tax number
		fakturNumber = parts[1]; // Faktur number
		// String part3 = parts[2]; // Customer tax's number
		// String part4 = parts[3]; // Datetime

		List<TCkTaxInvoice> tCkTaxInvoices = ckTaxInvoiceDao.findByFakturNumber(fakturNumber);
		if (tCkTaxInvoices.isEmpty()) {
			throw new Exception("Tax Invoice " + fakturNumber + " not found");
		}

		for (TCkTaxInvoice tCkTaxInvoice : tCkTaxInvoices) {
			if (!tCkTaxInvoice.getTiStatus().equals(CkTaxInvoiceEntityServiceImpl.TAX_INVOICE_STATUS_EXPORTED)) {
				throw new Exception("Tax Invoice " + fakturNumber + " not found or already completed");
			}
			jobId = tCkTaxInvoice.getTiJobNo();
			if (jobId != null) {
				try {
					// Save the file to the desired location
					savePath = coreSysparamDao.find(ICkConstant.KEY_ATTCH_BASE_LOCATION).getSysVal() + jobId + "/"
							+ fileName;
					byte[] fileData = file.getBytes();
					ckFileUtil.saveAttachmentWithJobId(jobId, fileName, fileData);
					tCkTaxInvoice.setTiDoc(savePath);
					tCkTaxInvoice.setTiStatus(CkTaxInvoiceEntityServiceImpl.TAX_INVOICE_STATUS_COMPLETED);
					tCkTaxInvoice.setTiDtLupd(new Date());
					tCkTaxInvoice.setTiUidLupd(principal.getUserAccnId());
					ckTaxInvoiceDao.update(tCkTaxInvoice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;

		}

		return savePath;
	}
	
	

	public String getAttachment(String dtoId)
			throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
		
		LOG.info("getAttachment");
		
		try {
			if (StringUtils.isBlank(dtoId))
				throw new ParameterException("param dtoId null or empty");

			TCkTaxInvoice tCkTaxInvoice = ckTaxInvoiceDao.find(dtoId);
			if (tCkTaxInvoice == null)
				throw new EntityNotFoundException("entity not found: " + dtoId);
			if (!StringUtils.isBlank(tCkTaxInvoice.getTiDoc())) {

				String base64ContentString = Base64Utils
						.encodeToString(IOUtils.toByteArray(Files.newInputStream(Paths.get(tCkTaxInvoice.getTiDoc()))));

				return base64ContentString;
			}
		} catch (Exception ex) {
			throw ex;
		}

		return null;

	}
}
