package com.guudint.clickargo.tax.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.guudint.clickargo.common.CkDateFormat;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.tax.dao.CkTaxInvoiceDao;
import com.guudint.clickargo.tax.dao.CkTaxReportDao;
import com.guudint.clickargo.tax.dto.CkTaxReport;
import com.guudint.clickargo.tax.model.TCkTaxInvoice;
import com.guudint.clickargo.tax.model.TCkTaxReport;
import com.guudint.clickargo.util.CkDateUtil;
import com.guudint.clickargo.util.CkNumberUtil;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.embed.TCoreAddress;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.util.email.SysParam;

@Service
public class CkTaxReportService {

	private static Logger LOG = Logger.getLogger(CkTaxReportService.class);
	
	@Autowired
	protected CkTaxInvoiceDao ckTaxInvoiceDao;
	@Autowired
	private CkTaxReportDao ckTaxReportDao;	
	@Autowired
	protected SysParam sysParam;
	@Autowired
	protected ICkSession ckSession;
	

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TCkTaxReport generateReport(List<TCkTaxInvoice> ckTaxInvoices, ServiceTypes serviceType) throws Exception {
		try {
			CkDateUtil dateUtil = new CkDateUtil(new Date());

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("efaktur");
			setHeaderTaxReport(sheet);
			setDetailTaxReport(sheet, ckTaxInvoices);
			
			String path = sysParam.getValString("CLICKARGO_ATTCH_BASE_LOCATION", "/home/vcc/appAttachments/clicargo/");
			File filePath = new File(path + File.separator + "TaxInvoice" + File.separator+ dateUtil.toStringFormat(CkDateFormat.Java.YYYY_MM_DD_HH_MM_SS) + ".xlsx");
			// create path
			filePath.getParentFile().mkdirs();
			
			FileOutputStream outputStream = new FileOutputStream(filePath);
			workbook.write(outputStream);
			workbook.close();
			
			TCkTaxReport tCkTaxReport = new TCkTaxReport(CkUtil.generateId(CkTaxReport.PREFIX_ID));
			
			tCkTaxReport.setTrService(serviceType.name());
			tCkTaxReport.setTrDoc(filePath.getAbsolutePath());
			tCkTaxReport.setTrName(filePath.getName());
			tCkTaxReport.setTrNumRecords(ckTaxInvoices.size());

			tCkTaxReport.setTrDtCreate(dateUtil.getDate());
			tCkTaxReport.setTrDtLupd(dateUtil.getDate());
			tCkTaxReport.setTrUidCreate("SYS");
			tCkTaxReport.setTrUidLupd("SYS");
			tCkTaxReport.setTrStatus('A');
			
			ckTaxReportDao.add(tCkTaxReport);
			
			for (TCkTaxInvoice tCkTaxInvoice : ckTaxInvoices) {
				tCkTaxInvoice.setTiStatus('E');
				ckTaxInvoiceDao.update(tCkTaxInvoice);
			}
			
			return tCkTaxReport;
			
		} catch (Exception e) {
			LOG.error("Error generateReport", e);
			throw e;
		}
	}

	private void setHeaderTaxReport(XSSFSheet sheet) {
		List<String> FK_COLUMN_NAME = Arrays.asList("FK", "KD_JENIS_TRANSAKSI", "FG_PENGGANTI", "NOMOR_FAKTUR",
				"MASA_PAJAK", "TAHUN_PAJAK", "TANGGAL_FAKTUR", "NPWP", "NAMA", "ALAMAT_LENGKAP", "JUMLAH_DPP",
				"JUMLAH_PPN", "JUMLAH_PPNBM", "ID_KETERANGAN_TAMBAHAN", "FG_UANG_MUKA", "UANG_MUKA_DPP",
				"UANG_MUKA_PPN", "UANG_MUKA_PPNBM", "REFERENSI", "KODE_DOKUMEN_PENDUKUNG");
		List<String> LT_COLUMN_NAME = Arrays.asList("LT", "NPWP", "NAMA", "JALAN", "BLOK", "NOMOR", "RT", "RW",
				"KECAMATAN", "KELURAHAN", "KABUPATEN", "PROPINSI", "KODE_POS", "NOMOR_TELEPON");
		List<String> OF_COLUMN_NAME = Arrays.asList("OF", "KODE_OBJEK", "NAMA", "HARGA_SATUAN", "JUMLAH_BARANG",
				"HARGA_TOTAL", "DISKON", "DPP", "PPN", "TARIF_PPNBM", "PPNBM");
		int rowNumber = 0, columnNumber = 0;
		Row rowHeaderFk = sheet.createRow(rowNumber++);
		for (String columnName : FK_COLUMN_NAME) {
			rowHeaderFk.createCell(columnNumber++).setCellValue(columnName);
		}
		columnNumber = 0;
		Row rowHeaderLt = sheet.createRow(rowNumber++);
		for (String columnName : LT_COLUMN_NAME) {
			rowHeaderLt.createCell(columnNumber++).setCellValue(columnName);
		}
		columnNumber = 0;
		Row rowHeaderOf = sheet.createRow(rowNumber++);
		for (String columnName : OF_COLUMN_NAME) {
			rowHeaderOf.createCell(columnNumber++).setCellValue(columnName);
		}
	}

	private void setDetailTaxReport(XSSFSheet sheet, List<TCkTaxInvoice> tCkTaxInvoices) {
		int rowNumber = 3;
		for (TCkTaxInvoice tCkTaxInvoice : tCkTaxInvoices) {
			TCoreAccn tCoreAccn = Optional.ofNullable(tCkTaxInvoice.getTCoreAccn()).orElse(new TCoreAccn());
			BigDecimal amount = Optional.ofNullable(tCkTaxInvoice.getTiAmt()).orElse(BigDecimal.ZERO);
			BigDecimal totalItem = BigDecimal.ONE;
			String address = "";
			TCoreAddress tCoreAddress = Optional.ofNullable(tCoreAccn.getAccnAddr()).orElse(new TCoreAddress());
			if (StringUtils.isNotBlank(tCoreAddress.getAddrLn1())) {
				address += tCoreAddress.getAddrLn1() + " ";
			}
			// if (StringUtils.isNotBlank(tCoreAddress.getAddrLn2())) {
			// address += tCoreAddress.getAddrLn2() + " ";
			// }
			// if (StringUtils.isNotBlank(tCoreAddress.getAddrLn3())) {
			// address += tCoreAddress.getAddrLn3() + " ";
			// }
			// if (StringUtils.isNotBlank(tCoreAddress.getAddrCity())) {
			// address += tCoreAddress.getAddrCity() + " ";
			// }
			// if (StringUtils.isNotBlank(tCoreAddress.getAddrProv())) {
			// address += tCoreAddress.getAddrProv() + " ";
			// }
			fk(sheet, rowNumber++, tCkTaxInvoice, tCoreAccn, address.trim(), amount, totalItem);
			lt(sheet, rowNumber++, tCkTaxInvoice, tCoreAccn, address.trim(), tCoreAddress.getAddrPcode());
			of(sheet, rowNumber++, tCkTaxInvoice, amount, totalItem);
		}
	}

	private void fk(XSSFSheet sheet, int rowNumber, TCkTaxInvoice tCkTaxInvoice, TCoreAccn tCoreAccn, String address,
			BigDecimal amount, BigDecimal totalItem) {
		CkDateUtil invoiceDate = new CkDateUtil(tCkTaxInvoice.getTiInvDtIssue());
		Row rowFk = sheet.createRow(rowNumber);
		int columnNumber = 0;
		rowFk.createCell(columnNumber++).setCellValue("FK");
		Cell transactionType = rowFk.createCell(columnNumber++);
		transactionType.setCellValue("01");
		Cell replacementFlag = rowFk.createCell(columnNumber++);
		replacementFlag.setCellValue("0");
		Cell fakturNumber = rowFk.createCell(columnNumber++);
		String tiNo = Optional.ofNullable(tCkTaxInvoice.getTiNo()).orElse("").substring(3).replaceAll("\\.", "");
		fakturNumber.setCellValue(StringUtils.leftPad(tiNo, 13, "0"));
		Cell taxPeriod = rowFk.createCell(columnNumber++);
		taxPeriod.setCellValue(invoiceDate.getMonth());
		Cell taxYear = rowFk.createCell(columnNumber++);
		taxYear.setCellValue(invoiceDate.getYear());
		Cell fakturDate = rowFk.createCell(columnNumber++);
		fakturDate.setCellValue(invoiceDate.toStringFormat(CkDateFormat.Java.DD_MM_YYYY));
		Cell taxNumber2 = rowFk.createCell(columnNumber++);
		taxNumber2.setCellValue(tCoreAccn.getAccnCoyRegn());
		Cell customerName2 = rowFk.createCell(columnNumber++);
		customerName2.setCellValue(tCoreAccn.getAccnName());
		Cell customerAddress2 = rowFk.createCell(columnNumber++);
		customerAddress2.setCellValue(address);
		Cell totalAmountBeforeTax = rowFk.createCell(columnNumber++);
		totalAmountBeforeTax.setCellValue(String.valueOf(amount.multiply(totalItem).longValue()));
		Cell totalVat = rowFk.createCell(columnNumber++);
		totalVat.setCellValue(amount.multiply(totalItem).multiply(CkNumberUtil.toBigDecimal(0.11)).longValue());
		Cell totalPpnbm = rowFk.createCell(columnNumber++);
		totalPpnbm.setCellValue("0");
		Cell additionalRemark = rowFk.createCell(columnNumber++);
		additionalRemark.setCellValue("");
		Cell downPaymentFlag = rowFk.createCell(columnNumber++);
		downPaymentFlag.setCellValue("0");
		Cell totalDownPayment = rowFk.createCell(columnNumber++);
		totalDownPayment.setCellValue("0");
		Cell vatDownPayment = rowFk.createCell(columnNumber++);
		vatDownPayment.setCellValue("0");
		Cell ppnbmDownPayment = rowFk.createCell(columnNumber++);
		ppnbmDownPayment.setCellValue("0");
		Cell reference = rowFk.createCell(columnNumber++);
		reference.setCellValue(tCkTaxInvoice.getTiInvNo());
		Cell documentCode = rowFk.createCell(columnNumber++);
		documentCode.setCellValue("");
	}

	private void lt(XSSFSheet sheet, int rowNumber, TCkTaxInvoice tCkTaxInvoice, TCoreAccn tCoreAccn, String address,
			String addrPosCode) {
		Row rowLt = sheet.createRow(rowNumber);
		int columnNumber = 0;
		rowLt.createCell(columnNumber++).setCellValue("LT");
		Cell taxNumber = rowLt.createCell(columnNumber++);
		String npwp = Optional.ofNullable(tCoreAccn.getAccnCoyRegn()).orElse("");
		taxNumber.setCellValue(StringUtils.leftPad(npwp, 15, "0"));
		Cell customerName = rowLt.createCell(columnNumber++);
		customerName.setCellValue(tCoreAccn.getAccnName());
		Cell customerAddress = rowLt.createCell(columnNumber++);
		customerAddress.setCellValue(address);
		Cell block = rowLt.createCell(columnNumber++);
		block.setCellValue("-");
		Cell number = rowLt.createCell(columnNumber++);
		number.setCellValue("-");
		Cell rt = rowLt.createCell(columnNumber++);
		rt.setCellValue("-");
		Cell rw = rowLt.createCell(columnNumber++);
		rw.setCellValue("-");
		Cell district = rowLt.createCell(columnNumber++);
		district.setCellValue("-");
		Cell village = rowLt.createCell(columnNumber++);
		village.setCellValue("-");
		Cell regency = rowLt.createCell(columnNumber++);
		regency.setCellValue("-");
		Cell province = rowLt.createCell(columnNumber++);
		province.setCellValue("-");
		Cell posCode = rowLt.createCell(columnNumber++);
		posCode.setCellValue(addrPosCode);
		Cell phoneNumber = rowLt.createCell(columnNumber++);
		phoneNumber.setCellValue("-");
		rowLt.createCell(columnNumber++).setCellValue("");
		rowLt.createCell(columnNumber++).setCellValue("");
		rowLt.createCell(columnNumber++).setCellValue("");
		rowLt.createCell(columnNumber++).setCellValue("");
		rowLt.createCell(columnNumber++).setCellValue("");
		rowLt.createCell(columnNumber++).setCellValue("");
	}

	private void of(XSSFSheet sheet, int rowNumber, TCkTaxInvoice tCkTaxInvoice, BigDecimal amount,
			BigDecimal totalItem) {
		Row rowOf = sheet.createRow(rowNumber);
		int columnNumber = 0;
		rowOf.createCell(columnNumber++).setCellValue("OF");
		Cell objectCode = rowOf.createCell(columnNumber++);
		objectCode.setCellValue("0");
		Cell serviceName = rowOf.createCell(columnNumber++);
		serviceName.setCellValue("PLATFORM FEE");
		Cell unitPrice = rowOf.createCell(columnNumber++);
		unitPrice.setCellValue(amount.longValue());
		Cell quantity = rowOf.createCell(columnNumber++);
		quantity.setCellValue(totalItem.longValue());
		Cell totalAmount = rowOf.createCell(columnNumber++);
		totalAmount.setCellValue(amount.multiply(totalItem).longValue());
		Cell discount = rowOf.createCell(columnNumber++);
		discount.setCellValue("0");
		Cell taxBasis = rowOf.createCell(columnNumber++);
		taxBasis.setCellValue(amount.longValue());
		Cell vat = rowOf.createCell(columnNumber++);
		vat.setCellValue(amount.multiply(CkNumberUtil.toBigDecimal(0.11)).longValue());
		Cell ppnbmTariff = rowOf.createCell(columnNumber++);
		ppnbmTariff.setCellValue("0");
		Cell ppnbm = rowOf.createCell(columnNumber++);
		ppnbm.setCellValue("0");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
		rowOf.createCell(columnNumber++).setCellValue("");
	}
	////////////////
	
	public String getAttachment(String trId)
			throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
		LOG.debug("getAttachment");
		try {
			if (StringUtils.isBlank(trId))
				throw new ParameterException("param dtoId null or empty");

			TCkTaxReport tCkTaxReport = ckTaxReportDao.find(trId);
			if (tCkTaxReport == null)
				throw new EntityNotFoundException("entity not found: " + trId);
			if (!StringUtils.isBlank(tCkTaxReport.getTrDoc())) {

				String base64ContentString = Base64Utils
						.encodeToString(IOUtils.toByteArray(Files.newInputStream(Paths.get(tCkTaxReport.getTrDoc()))));
				tCkTaxReport.setTrStatus(CkTaxReportEntityServiceImpl.TAX_REPORT_STATUS_DOWNLOADED);
				tCkTaxReport.setTrDtLupd(new Date());
				
				Principal principal = ckSession.getPrincipal();
				if (principal == null) {
					throw new ParameterException("principal is null");
				}
				tCkTaxReport.setTrUidLupd(principal.getUserAccnId());
				ckTaxReportDao.update(tCkTaxReport);

				return base64ContentString;
			}
		} catch (Exception ex) {
			throw ex;
		}

		return null;

	}
}
