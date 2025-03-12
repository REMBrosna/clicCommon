package com.guudint.clickargo.common;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.SysParamService;
import com.vcc.camelone.config.model.TCoreSysparam;

/**
 * Clickargo common file util. FileUtil.java found in clictruck will be moved
 * here or may be duplicated here to be used by other services such as ClicDO,
 * ClicGatePass, etc.
 */
@Service
public class CkFileUtil {

	private static String baseAccnAttachLocation;
	private static String baseAttachmentLocation;

	@Autowired
	@Qualifier("coreSysparamDao")
	protected GenericDao<TCoreSysparam, String> coreSysparamDao;

	@Autowired
	SysParamService sysParamService;

	public String saveAttachment(String folder, String filename, byte[] data) throws Exception {

		if (StringUtils.isBlank(baseAccnAttachLocation))
			baseAccnAttachLocation = sysParamService.getValString(ICkConstant.KEY_CK_ACCN_ATTACH_BASE_LOCATION);
		
		//if after getting from sysparam it's null, throw exception
		if (StringUtils.isBlank(baseAccnAttachLocation))
			throw new ProcessingException("baseAccnAttachLocation is not configured");


		Path dir = Paths.get(baseAccnAttachLocation.concat(folder));
		if (!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		File jobDir = new File(baseAccnAttachLocation.concat(folder));
		File file = new File(jobDir.getAbsolutePath(), filename);
		FileOutputStream output = new FileOutputStream(file);
		output.write(data);
		output.close();
		return file.getAbsolutePath();
	}
	

	public String saveAttachmentWithJobId(String jobId, String filename, byte[] data) throws Exception {
		if (StringUtils.isBlank(filename))
			throw new ParameterException("param filename null or empty");

		if (data == null)
			throw new ParameterException("param data null or empty");

		if (StringUtils.isBlank(baseAttachmentLocation))
			baseAttachmentLocation = sysParamService.getValString(ICkConstant.KEY_ATTCH_BASE_LOCATION);

		Path dir = Paths.get(baseAttachmentLocation.concat(jobId));
		if (!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		File jobDir = new File(baseAttachmentLocation.concat(jobId));
		File file = new File(jobDir.getAbsolutePath(), filename);
		FileOutputStream output = new FileOutputStream(file);
		output.write(data);
		output.close();
		return file.getAbsolutePath();
	}
}
