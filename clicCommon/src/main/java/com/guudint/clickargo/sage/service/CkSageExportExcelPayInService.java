package com.guudint.clickargo.sage.service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

@Service
public class CkSageExportExcelPayInService extends CkSageExportExcelService {

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
		row.setHeightInPoints(25);

		HSSFCell cell = row.createCell(cellid++);
		cell.setCellValue("service");
		cell = row.createCell(cellid++);
		cell.setCellValue("type");
		cell = row.createCell(cellid++);
		cell.setCellValue("reference");
		cell = row.createCell(cellid++);
		cell.setCellValue("dateTime");
		cell = row.createCell(cellid++);
		cell.setCellValue("payTotal");

		cell = row.createCell(cellid++);
		cell.setCellValue("CunsumerId");
		cell = row.createCell(cellid++);
		cell.setCellValue("docType");
		cell = row.createCell(cellid++);
		cell.setCellValue("DocumentNo");
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
		cell.setCellValue("invoiceNo");

	}

}
