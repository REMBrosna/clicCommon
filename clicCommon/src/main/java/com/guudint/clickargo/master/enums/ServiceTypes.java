package com.guudint.clickargo.master.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ServiceTypes {

	CLICKARGO("CLICKARGO", "CLICKARGO", "CK"), 
	CLICDO("CLICDO", "CLICDO", "CKDO"),
	CLICTRUCK("CLICTRUCK", "CLICTRUCK", "CKT"), 
	CLICDECLARE("CLICDECLARE", "CLICDECLARE", "CKD"),
	CLICGP("CLICGP","CLICGP","CKG");
	

	private final String id;
	private final String desc;
	private final String appsCode;

	ServiceTypes(String id, String desc, String appsCode) {
		this.id = id;
		this.desc = desc;
		this.appsCode = appsCode;
	}

	public String getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public String getAppsCode() {
		return appsCode;
	}

	public static String getAppsCodeByServiceType(String serviceType) {
		Optional<ServiceTypes> svcType = Arrays.stream(values()).filter(e -> e.getId().equalsIgnoreCase(serviceType)).findFirst();
		if (svcType.isPresent())
			return svcType.get().getAppsCode();
		return null;
	}
	
	public static String getServiceTypeByAppsCode(String appsCode) {
		Optional<ServiceTypes> svcType = Arrays.stream(values()).filter(e -> e.getAppsCode().equalsIgnoreCase(appsCode)).findFirst();
		if (svcType.isPresent())
			return svcType.get().getId();
		return null;
		
	}

}
