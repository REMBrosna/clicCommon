package com.guudint.clickargo.tax.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guudint.clickargo.tax.service.CkTaxInvoiceService;
import com.guudint.clickargo.tax.service.CkTaxReportService;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.AbstractPortalController;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceError;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.ServiceStatus.STATUS;

@CrossOrigin
@RestController("commonTaxController")
@RequestMapping("/api/v1/clickargo/tax")
public class TaxController extends AbstractPortalController{

	private static Logger LOG = Logger.getLogger(TaxController.class);
	
	@Autowired
	CkTaxInvoiceService ckTaxInvoiceService;

	@Autowired
	CkTaxReportService ckTaxReportService;

	@PostMapping("/uploadTaxPdfFile")
    public ResponseEntity<Object> handleTaxInvoiceUpload(@RequestParam("file") MultipartFile file) throws Exception{
		LOG.debug("upload tax invoice");
        ServiceStatus serviceStatus = new ServiceStatus();
        Principal principal = getPrincipal();
		if (null == principal) {
			throw new ProcessingException("principal is null");
		}
		
		try {
			String savePath = ckTaxInvoiceService.uploadTaxInvoice(file, principal);
			if (savePath != null) {
				return ResponseEntity.ok(savePath);
             } else {
            	 LOG.error("upload tax invoice");
      			serviceStatus.setStatus(STATUS.EXCEPTION);
      			serviceStatus.setErr(new ServiceError(-100, "Error uploading file"));
      			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
             }
        }catch(Exception e) {
        	LOG.error("upload tax invoice");
 			serviceStatus.setStatus(STATUS.EXCEPTION);
 			serviceStatus.setErr(new ServiceError(-100, e));
 			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    } 

	@GetMapping("/downloadTaxPdfFile/{tsId}")
    public ResponseEntity<Object> downloadTaxPdfFile(@PathVariable String tsId) throws Exception{
		LOG.debug("download tax invoice");
        ServiceStatus serviceStatus = new ServiceStatus();
        Principal principal = getPrincipal();
		if (null == principal) {
			throw new ProcessingException("principal is null");
		}
		
		try {
			String base64Str = ckTaxInvoiceService.getAttachment(tsId);
			
			if (base64Str != null) {
				return ResponseEntity.ok(base64Str);
             } else {
            	 LOG.error("upload tax invoice");
      			serviceStatus.setStatus(STATUS.EXCEPTION);
      			serviceStatus.setErr(new ServiceError(-100, "Error uploading file"));
      			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
             }
        }catch(Exception e) {
        	LOG.error("upload tax invoice");
 			serviceStatus.setStatus(STATUS.EXCEPTION);
 			serviceStatus.setErr(new ServiceError(-100, e));
 			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    } 

	@GetMapping("/downloadTaxExcelFile/{tsId}")
    public ResponseEntity<Object> downloadTaxExcelFile(@PathVariable String tsId) throws Exception{
		LOG.debug("download tax invoice");
        ServiceStatus serviceStatus = new ServiceStatus();
        Principal principal = getPrincipal();
		if (null == principal) {
			throw new ProcessingException("principal is null");
		}
		
		try {
			String base64Str = ckTaxReportService.getAttachment(tsId);
			
			if (base64Str != null) {
				return ResponseEntity.ok(base64Str);
             } else {
            	 LOG.error("upload tax invoice");
      			serviceStatus.setStatus(STATUS.EXCEPTION);
      			serviceStatus.setErr(new ServiceError(-100, "Error uploading file"));
      			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
             }
        }catch(Exception e) {
        	LOG.error("upload tax invoice");
 			serviceStatus.setStatus(STATUS.EXCEPTION);
 			serviceStatus.setErr(new ServiceError(-100, e));
 			return new ResponseEntity<Object>(serviceStatus, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    } 
}
