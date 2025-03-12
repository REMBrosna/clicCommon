package com.guudint.clickargo.credit.event;

import org.springframework.context.ApplicationEvent;
import com.guudint.clickargo.credit.dto.CkCreditRequest;

public class CreditRequestEvent extends ApplicationEvent {

	// Static attributes
	private static final long serialVersionUID = 2702400960489167617L;
	
    // Attributes
	private CkCreditRequest ckCreditRequest;
    
    // Constructor
	public CreditRequestEvent(Object source, CkCreditRequest ckCreditRequest) {
		super(source);
		this.ckCreditRequest = ckCreditRequest;
	}

	// Properties
	/////////////
	public CkCreditRequest getCkCreditRequest() {
		return ckCreditRequest;
	}

	public void setCkCreditRequest(CkCreditRequest ckCreditRequest) {
		this.ckCreditRequest = ckCreditRequest;
	}

}
