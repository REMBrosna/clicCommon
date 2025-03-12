package com.guudint.clickargo.external.services;

import com.guudint.clickargo.external.dto.ActivateVARequest;
import com.guudint.clickargo.external.dto.ActivateVAResponse;
import com.guudint.clickargo.external.dto.CancelPaymentRequest;
import com.guudint.clickargo.external.dto.CancelPaymentResponse;
import com.guudint.clickargo.external.dto.CreateFundTransferRequest;
import com.guudint.clickargo.external.dto.CreateFundTransferResponse;
import com.guudint.clickargo.external.dto.CreateFundTransferVARequest;
import com.guudint.clickargo.external.dto.CreateFundTransferVAResponse;
import com.guudint.clickargo.external.dto.GetFundTransferStatusRequest;
import com.guudint.clickargo.external.dto.GetFundTransferStatusResponse;
import com.guudint.clickargo.external.dto.GetVAStatusRequest;
import com.guudint.clickargo.external.dto.LoginResponse;
import com.guudint.clickargo.external.dto.MakePaymentRequest;
import com.guudint.clickargo.external.dto.MakePaymentResponse;
import com.guudint.clickargo.external.dto.VaResponse;
import com.vcc.camelone.cac.model.Principal;

public interface IPaymentGateway {

	LoginResponse login(String node, String serviceID) throws Exception;

	VaResponse va(GetVAStatusRequest getVAStatusRequest) throws Exception;

//	CreateStaticVAResponse createStaticVirtualAccount(CreateStaticVARequest createVARequest) throws Exception;

//	CreateVAResponse createVirtualAccount(CreateVARequest createVARequest) throws Exception;

	GetFundTransferStatusResponse getFundTransferStatus(GetFundTransferStatusRequest getFundTransferStatusRequest)
			throws Exception;

	CreateFundTransferVAResponse createFundTransferVA(CreateFundTransferVARequest createFundTransferRequest) throws Exception;
	
	CreateFundTransferResponse createFundTransferBIFast(CreateFundTransferRequest createFundTransferRequest) throws Exception;

	MakePaymentResponse makePayment(MakePaymentRequest makePaymentRequest) throws Exception;

	CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest) throws Exception;

	ActivateVAResponse activateVA(ActivateVARequest activateVARequest) throws Exception;

	void paymentCallback(Principal principal) throws Exception;

	void createLedger(Principal principal) throws Exception;

	void logout() throws Exception;
}
