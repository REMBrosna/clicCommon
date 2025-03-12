package com.guudint.clickargo.tax.dao.impl;

import org.springframework.stereotype.Service;

import com.guudint.clickargo.tax.dao.CkTaxReportDao;
import com.guudint.clickargo.tax.model.TCkTaxReport;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Service("ckTaxReportDao")
public class CkTaxReportDaoImpl extends GenericDaoImpl<TCkTaxReport, String> implements CkTaxReportDao {

}
