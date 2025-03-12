package com.guudint.clickargo.sage.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.sage.dao.CkSageIntegrationDao;
import com.guudint.clickargo.sage.model.TCkSageIntegration;
import com.vcc.camelone.common.attach.dto.CoreAttach;
import com.vcc.camelone.util.email.SysParam;


@Service
public class CkSageService {

	private static Logger log = Logger.getLogger(CkSageService.class);


	@Autowired
	private CkSageIntegrationDao ckSageIntegrationDao;

	// @Autowired
	// @Qualifier("coreSysparamDao")
	// protected GenericDao<TCoreSysparam, String> coreSysparamDao;

	@Autowired
	protected SysParam sysParam;

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public String getFileBody(String filePathBase64) throws Exception {

		// decode filePath
		String filePath = new String(Base64.getDecoder().decode(filePathBase64.getBytes()));
		
		String fileFolder = sysParam.getValString("CLICTRUCK-STORE-FILEPATH", "/home,/data");

		boolean isInSecurityFolder = Arrays.asList(fileFolder.split(",")).stream()
				.anyMatch(f -> filePath.indexOf(f) == 0);

		if (!isInSecurityFolder) {
			log.error("" + filePath + " not in " + fileFolder);
			throw new Exception(filePath + " is not correct.");
		}
		Path path = Paths.get(filePath);

		byte[] arr = Files.readAllBytes(path);
		
		String base64FileBody = Base64.getEncoder().encodeToString(arr);
		
		return base64FileBody;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public CoreAttach getFileBodyBySageId(String sageId, boolean isUpdate2DownloadStatus) throws Exception {

		// decode filePath
		TCkSageIntegration sage = ckSageIntegrationDao.find(sageId);
		
		if( null == sage) {
			log.error("Not find sage: " + sageId );
			throw new Exception(sageId + " is not correct.");
		}
		
		String filePath = sage.getSintLocImport();

		byte[] arr = Files.readAllBytes(Paths.get(filePath));

		CoreAttach coreAttach = new CoreAttach();
		coreAttach.setAttData(arr);
		
		String[] pathArray = filePath.split("/");
		coreAttach.setAttName(pathArray[pathArray.length-1]);
		
		if(isUpdate2DownloadStatus ) {
			//sage.setSageStatus('D');
			//ckCtSageDao.saveOrUpdate(sage);
		}
		
		return coreAttach;
	}
	
}
