package com.guudint.clickargo.external.services;

import com.guudint.clickargo.estamp.dto.AuthTokenResponse;
import com.guudint.clickargo.estamp.dto.StampCallbackRequest;
import com.guudint.clickargo.estamp.dto.StampDownloadResponse;
import com.guudint.clickargo.estamp.dto.StampRequest;
import com.guudint.clickargo.estamp.dto.StampResponse;
import com.guudint.clickargo.estamp.model.TCkEstampDoc;

public interface IStampGateway {

	AuthTokenResponse login() throws Exception;

	/**
	 * Creates record for the document in t_ck_estamp_doc table.
	 */
	void stampDocument(StampRequest request) throws Exception;

	/**
	 * Sends the document for estamping to eMeterai
	 */
	void stampSendDocument(TCkEstampDoc tCkEstampDoc, AuthTokenResponse authToken) throws Exception;

	/**
	 * Checks the status of the document sent for estamping.
	 */
	void stampDocumentDetail(TCkEstampDoc tCkEstampDoc, AuthTokenResponse authToken) throws Exception;

	/**
	 * Downloads the e-stamped document
	 */
	StampDownloadResponse stampDocumentDownload(TCkEstampDoc tCkEstampDoc, AuthTokenResponse authToken) throws Exception;

	StampResponse stampCallback(StampCallbackRequest request) throws Exception;
}
