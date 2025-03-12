package com.guudint.clickargo.sage.dto;

import java.util.Date;

import com.guudint.clickargo.master.enums.ServiceTypes;

public class CkSageParamDto {
	
	String tableName;
	String dateFieldName;
	
	String fieldNamePrefix;
	String sheetName;
	
	Date beginDate;
	Date endDate;

	ServiceTypes serviceType;
	CkCtMstSageIntStateEnum sageIntSate;
	CkCtMstSageIntTypeEnum sageIntType;
	
	public CkSageParamDto() {
		super();
	}
	
	public CkSageParamDto(String tableName, String dateFieldName, Date beginDate, Date endDate) {
		super();
		this.tableName = tableName;
		this.dateFieldName = dateFieldName;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDateFieldName() {
		return dateFieldName;
	}
	public void setDateFieldName(String dateFieldName) {
		this.dateFieldName = dateFieldName;
	}

	public String getFieldNamePrefix() {
		return fieldNamePrefix;
	}

	public void setFieldNamePrefix(String fieldNamePrefix) {
		this.fieldNamePrefix = fieldNamePrefix;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ServiceTypes getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypes serviceType) {
		this.serviceType = serviceType;
	}

	public CkCtMstSageIntStateEnum getSageIntSate() {
		return sageIntSate;
	}

	public void setSageIntSate(CkCtMstSageIntStateEnum sageIntSate) {
		this.sageIntSate = sageIntSate;
	}

	public CkCtMstSageIntTypeEnum getSageIntType() {
		return sageIntType;
	}

	public void setSageIntType(CkCtMstSageIntTypeEnum sageIntType) {
		this.sageIntType = sageIntType;
	}
	
}
