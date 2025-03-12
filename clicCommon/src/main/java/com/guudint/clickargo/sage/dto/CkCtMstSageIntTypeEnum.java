package com.guudint.clickargo.sage.dto;

import java.util.EnumSet;

public enum CkCtMstSageIntTypeEnum {

	BOOKING("AR"), PAYMENT_IN("PI"), PAYMENT_OUT("PO");

	private String fileName;

	private CkCtMstSageIntTypeEnum(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public static CkCtMstSageIntTypeEnum findByFileName(String fileName) {

		return EnumSet.allOf(CkCtMstSageIntTypeEnum.class).stream().filter(a -> a.getFileName().endsWith(fileName))
				.findFirst().get();

	}
}
