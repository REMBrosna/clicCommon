package com.guudint.clickargo.sage.service;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

@Service
public class CkSageExportExcelBookingService extends CkSageExportExcelService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkSageExportExcelBookingService.class);


	@Override
	protected void setSheetFirstRow(HSSFSheet sheet) {

		sheet.setColumnWidth(0, 256 * 15);
		sheet.setColumnWidth(1, 256 * 10);
		sheet.setColumnWidth(2, 256 * 20);
		sheet.setColumnWidth(3, 256 * 20);
		sheet.setColumnWidth(4, 256 * 15);
		
		sheet.setColumnWidth(5, 256 * 20);
		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 25);
		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 10);

		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 15);
		sheet.setColumnWidth(4, 256 * 15);
		
		int rowid = 0;
		int cellid = 0;

		HSSFRow row = sheet.createRow(rowid);

		HSSFCell cell = row.createCell(cellid++);
		cell.setCellValue("service");
		cell = row.createCell(cellid++);
		cell.setCellValue("type");
		cell = row.createCell(cellid++);
		cell.setCellValue("reference");
		cell = row.createCell(cellid++);
		cell.setCellValue("dateTime");
		cell = row.createCell(cellid++);
		cell.setCellValue("typeid");
		
		
		cell = row.createCell(cellid++);
		cell.setCellValue("Cust./ProvicerId");
		cell = row.createCell(cellid++);
		cell.setCellValue("tranType");
		cell = row.createCell(cellid++);
		cell.setCellValue("DocNo");
		cell = row.createCell(cellid++);
		cell.setCellValue("issuedDate");
		cell = row.createCell(cellid++);
		cell.setCellValue("ccy");
		
		cell = row.createCell(cellid++);
		cell.setCellValue("amount");
		cell = row.createCell(cellid++);
		cell.setCellValue("vat");
		cell = row.createCell(cellid++);
		cell.setCellValue("duty");
		cell = row.createCell(cellid++);
		cell.setCellValue("terms");
		cell = row.createCell(cellid++);
		cell.setCellValue("taxNo");
		cell = row.createCell(cellid++);
		cell.setCellValue("total");

	}
}
