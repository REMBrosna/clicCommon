/**
 * 
 */
package com.guudint.clickargo.common;

/**
 * @author billy
 *
 */
public interface ICkConstant {

	public static final String DASH = "-";	
	
	public static final String PREFIX_PARENT_JOB = "CKJOB";
	
	public static final String PREFIX_JOB_ATT = "CKJA";
	
	public static final String PREFIX_DO_CO_JOB_ATT = "CKDOJA";

	public static final String PREFIX_PARENT_TASK = "CKTSK";

	public static final String PREFIX_TASK_DOI_FF = "CKTDF";

	public static final String PREFIX_TASK_DOI_FF_DO = "CKTDO";

	public static final String PREFIX_JOB_DOI_FF_DO = "CKJDO";
	
	public static final String PREFIX_JOB_DOI = "CKDOJ";

	public static final String PREFIX_JOB_DOI_CO = "DOCO";
	
	public static final String PREFIX_JOB_QUERY = "JQRY";
	
	public static final String PREFIX_ACC_ATT = "CKAATT";
	
	public static final String PREFIX_TRIP_ATT = "CKCTTA";
	
	//SysParam
	public static final String CK_DEF_SL_ACCN = "CLICDO_DEF_SL_ACCN";
	public static final String KEY_CLICDO_ATTCH_BASE_LOCATION = "CLICDO_ATTCH_BASE_LOCATION";

	// Payment
	String PREFIX_PAYMENT_TXN = "CKPTX";

	String PREFIX_PAYMENT_TXN_INVOICE = "CKPTI";

	String PREFIX_PAYMENT_LEDGER = "CKPYL";

	String PREFIX_PAYMENT_AUDIT = "CKPYA";

	String PREFIX_PAYMENT_TXN_ATT_INVOICE = "CKPTAI";

	String PREFIX_SVC_JOURNAL = "CKSVJL";

	String PREFIX_DO_SL_JOURNAL = "CKSLJL";
	
	String PREFIX_M = "M";
	
	String PREFIX_DO ="DO";
	
	//
	public String VEHICLE_TYPE_UNDEFINE = "UNDEFINE";
	
	String APP_CODE_CKT = "CKT";
	
	public static final String KEY_CLICKARGO_COUNTRY_CONFIG = "CLICKARGO_COUNTRY_CONFIG";
	
	// Assuming dash is allowed, space not allowed, i.e +123-4567
	String PHONE_PATTERN = "^[+]{1}[(]{0,1}[0-9]{1,4}[)]{0,1}[\\d\\./0-9]*$";
	
	//Clickargo Account Profile Base Location
	String KEY_CK_ACCN_ATTACH_BASE_LOCATION = "CLICKARGO_ACCN_ATTACH_BASE_LOCATION";
	
	public static final String KEY_ATTCH_BASE_LOCATION = "CLICARGO_ATTCH_BASE_LOCATION";
	
}
