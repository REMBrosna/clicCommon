package com.guudint.clickargo.common.event.listener;

import com.vcc.camelone.cac.model.Principal;

public interface IMonitoringListener<TCkCtVehExt, CkCtVehExt>  {
	
	public void sendEmailMonitoring(CkCtVehExt dto, Principal principal) throws Exception;
}
