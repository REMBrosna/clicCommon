package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guudint.clickargo.common.model.TCkAccn;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkAccn extends AbstractDTO<CkAccn, TCkAccn> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1L;

	private String caccnId;

	@JsonProperty("TCoreAccn")
	private CoreAccn TCoreAccn;
	private String caccnFinancingType;
	private String caccnFinancer;

	private String caccnExcelTemplate;
	private String caccnExcelTemplateExample;
	private String caccnEpodJrxml; //CACCN_EPOD_JRXML
	private String caccnEpodService; //CACCN_EPOD_SERVICE
	private String caccnIspotSubAccn;
	
	private Boolean caccnWhatsapp;
	private Boolean caccnSms;
	private Boolean caccnCo2x;
	private Boolean caccnRoutePlanning;
	
	private char caccnStatus;
	private Date caccnDtCreate;
	private String caccnUidCreate;
	private Date caccnDtLupd;
	private String caccnUidLupd;

	// Constructors
	///////////////
	public CkAccn() {
	}

	public CkAccn(TCkAccn entity) {
		super(entity);
	}

	public CkAccn(CoreAccn TCoreAccn, String caccnFinancer, char caccnStatus, Date caccnDtCreate,
			String caccnUidCreate) {
		this.TCoreAccn = TCoreAccn;
		this.caccnFinancer = caccnFinancer;
		this.caccnStatus = caccnStatus;
		this.caccnDtCreate = caccnDtCreate;
		this.caccnUidCreate = caccnUidCreate;
	}

	public CkAccn(CoreAccn TCoreAccn, String caccnFinancer, char caccnStatus, Date caccnDtCreate,
			String caccnUidCreate, Date caccnDtLupd, String caccnUidLupd) {
		this.TCoreAccn = TCoreAccn;
		this.caccnFinancer = caccnFinancer;
		this.caccnStatus = caccnStatus;
		this.caccnDtCreate = caccnDtCreate;
		this.caccnUidCreate = caccnUidCreate;
		this.caccnDtLupd = caccnDtLupd;
		this.caccnUidLupd = caccnUidLupd;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkAccn o) {
		return 0;
	}

	@Override
	public void init() {

	}

	public String getCaccnId() {
		return this.caccnId;
	}

	public void setCaccnId(String caccnId) {
		this.caccnId = caccnId;
	}

	@JsonProperty("TCoreAccn")
	public CoreAccn getTCoreAccn() {
		return this.TCoreAccn;
	}

	public void setTCoreAccn(CoreAccn TCoreAccn) {
		this.TCoreAccn = TCoreAccn;
	}

	public String getCaccnFinancer() {
		return this.caccnFinancer;
	}

	public void setCaccnFinancer(String caccnFinancer) {
		this.caccnFinancer = caccnFinancer;
	}
	
	public String getCaccnExcelTemplate() {
		return caccnExcelTemplate;
	}

	public void setCaccnExcelTemplate(String caccnExcelTemplate) {
		this.caccnExcelTemplate = caccnExcelTemplate;
	}

	public String getCaccnExcelTemplateExample() {
		return caccnExcelTemplateExample;
	}

	public void setCaccnExcelTemplateExample(String caccnExcelTemplateExample) {
		this.caccnExcelTemplateExample = caccnExcelTemplateExample;
	}

	public String getCaccnEpodJrxml() {
		return caccnEpodJrxml;
	}

	public void setCaccnEpodJrxml(String caccnEpodJrxml) {
		this.caccnEpodJrxml = caccnEpodJrxml;
	}

	public String getCaccnEpodService() {
		return caccnEpodService;
	}

	public void setCaccnEpodService(String caccnEpodService) {
		this.caccnEpodService = caccnEpodService;
	}

	public String getCaccnIspotSubAccn() {
		return caccnIspotSubAccn;
	}

	public void setCaccnIspotSubAccn(String caccnIspotSubAccn) {
		this.caccnIspotSubAccn = caccnIspotSubAccn;
	}

	public Boolean getCaccnWhatsapp() {
		return this.caccnWhatsapp;
	}

	public void setCaccnWhatsapp(Boolean caccnWhatsapp) {
		this.caccnWhatsapp = caccnWhatsapp;
	}

	public Boolean getCaccnSms() {
		return this.caccnSms;
	}

	public void setCaccnSms(Boolean caccnSms) {
		this.caccnSms = caccnSms;
	}

	public Boolean getCaccnCo2x() {
		return this.caccnCo2x;
	}

	public void setCaccnCo2x(Boolean caccnCo2x) {
		this.caccnCo2x = caccnCo2x;
	}

	public Boolean getCaccnRoutePlanning() {
		return this.caccnRoutePlanning;
	}

	public void setCaccnRoutePlanning(Boolean caccnRoutePlanning) {
		this.caccnRoutePlanning = caccnRoutePlanning;
	}

	public char getCaccnStatus() {
		return this.caccnStatus;
	}

	public void setCaccnStatus(char caccnStatus) {
		this.caccnStatus = caccnStatus;
	}

	public Date getCaccnDtCreate() {
		return this.caccnDtCreate;
	}

	public void setCaccnDtCreate(Date caccnDtCreate) {
		this.caccnDtCreate = caccnDtCreate;
	}

	public String getCaccnUidCreate() {
		return this.caccnUidCreate;
	}

	public void setCaccnUidCreate(String caccnUidCreate) {
		this.caccnUidCreate = caccnUidCreate;
	}

	public Date getCaccnDtLupd() {
		return this.caccnDtLupd;
	}

	public void setCaccnDtLupd(Date caccnDtLupd) {
		this.caccnDtLupd = caccnDtLupd;
	}

	public String getCaccnUidLupd() {
		return this.caccnUidLupd;
	}

	public void setCaccnUidLupd(String caccnUidLupd) {
		this.caccnUidLupd = caccnUidLupd;
	}

	public String getCaccnFinancingType() {
		return caccnFinancingType;
	}

	public void setCaccnFinancingType(String caccnFinancingType) {
		this.caccnFinancingType = caccnFinancingType;
	}

}
