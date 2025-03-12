package com.guudint.clickargo.manageaccn.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.validator.CkFieldValidatorUtils;
import com.guudint.clickargo.validator.CkFieldValidatorUtils.ErrorType;
import com.guudint.clickargo.validator.IValidate;
import com.vcc.camelone.common.COAbstractEntity;

public class CkAccnSuppDocsJson extends COAbstractEntity<CkAccnSuppDocsJson> implements IValidate {

	private static final long serialVersionUID = -3816158612361845325L;

	private int seq;
	private String id;
	private String desc;
	private boolean isMandatory;
	private String attType;
	private String wfTypeId;
	private String wfTypeDesc;
	private String attId;
	private String attReferenceid;
	private String attName;
	private int attSize;
	private byte[] attData;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getAttReferenceid() {
		return attReferenceid;
	}

	public void setAttReferenceid(String attReferenceid) {
		this.attReferenceid = attReferenceid;
	}

	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}

	public int getAttSize() {
		return attSize;
	}

	public void setAttSize(int attSize) {
		this.attSize = attSize;
	}

	public byte[] getAttData() {
		return attData;
	}

	public void setAttData(byte[] attData) {
		this.attData = attData;
	}

	public String getAttType() {
		return attType;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}
	

	/**
	 * @return the wfTypeId
	 */
	public String getWfTypeId() {
		return wfTypeId;
	}

	/**
	 * @param wfType the wfTypeId to set
	 */
	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	@Override
	public int compareTo(CkAccnSuppDocsJson o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the wfTypeDesc
	 */
	public String getWfTypeDesc() {
		return wfTypeDesc;
	}

	/**
	 * @param wfTypeDesc the wfTypeDesc to set
	 */
	public void setWfTypeDesc(String wfTypeDesc) {
		this.wfTypeDesc = wfTypeDesc;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ValidationError> validate() {
		List<ValidationError> errorList = new ArrayList<ValidationError>();
		ValidationError error = null;

		error = CkFieldValidatorUtils.rejectIfEmptyOrWhitespace(String.valueOf(this.getAttData()), "attData");
		if (Optional.ofNullable(error).isPresent())
			errorList.add(error);

		error = CkFieldValidatorUtils.rejectIfEmptyOrWhitespace(this.getAttType(), "attType");
		if (Optional.ofNullable(error).isPresent())
			errorList.add(error);

		error = CkFieldValidatorUtils.isNumeric(String.valueOf(this.getAttSize()), "attSize");
		if (Optional.ofNullable(error).isPresent())
			errorList.add(error);

		if (this.getAttData() == null || this.getAttData().length < 0) {
			errorList.add(new ValidationError("attData", ErrorType.MANDATORY_ERR.name(), "attData is Required"));
		}

		return errorList;
	}

}
