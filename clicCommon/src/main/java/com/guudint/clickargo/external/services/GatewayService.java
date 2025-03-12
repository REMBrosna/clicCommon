package com.guudint.clickargo.external.services;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.guudint.clickargo.external.dto.BaseResponse;
import com.guudint.clickargo.external.dto.GatewayConfig;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.config.model.TCoreSysparam;

/**
 * Service class for external api.
 */
public class GatewayService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(GatewayService.class);

	public static final String KEY_GW_URL = "url";
	public static final String KEY_GW_AUTH_USERNAME = "uid";
	public static final String KEY_GW_AUTH_PWD = "authPwd";

	@Autowired
	@Qualifier("coreSysparamDao")
	protected GenericDao<TCoreSysparam, String> sysParamDao;

	protected static final String AUTH_HEADER_KEY = "Authorization";
	protected static final String AUTH_HEADER_VALUE_PREFIX = "Bearer ";

	protected static ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	protected Client getClient() {
		ClientConfig configuration = new ClientConfig();
		configuration.property(ClientProperties.CONNECT_TIMEOUT, 10_000);
		configuration.property(ClientProperties.READ_TIMEOUT, 50_000);
		configuration.register(JacksonJsonProvider.class);

		return ClientBuilder.newClient(configuration);
	}

	protected Client getClient(boolean isHttps) throws Exception {
		ClientConfig configuration = new ClientConfig();
		configuration.property(ClientProperties.CONNECT_TIMEOUT, 10_000);
		configuration.property(ClientProperties.READ_TIMEOUT, 50_000);
		configuration.register(JacksonJsonProvider.class);

		if (isHttps) {
			return JerseyHttpClientFactory.getJerseyHTTPSClient("SSL", configuration);
		}

		return ClientBuilder.newClient(configuration);
	}

	protected Builder initInvocationAuthBuilder(String apiUrl, String token) {
		try {
			if (StringUtils.isBlank(apiUrl))
				throw new Exception("apiUrl is null");
			if (StringUtils.isBlank(token))
				throw new Exception("token is null");

			return this.getClient().target(apiUrl).request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			log.error("setUpInvocationBuilder error" + e);
		}

		return null;
	}

	protected Builder initInvocationAuthBuilder(String apiUrl, String token, Map<String, Object> reqParam) {
		try {
			if (StringUtils.isBlank(apiUrl))
				throw new Exception("apiUrl is null");
			if (StringUtils.isBlank(token))
				throw new Exception("token is null");

			WebTarget target = this.getClient().target(apiUrl);
			if (reqParam != null && !reqParam.isEmpty()) {
				for (Map.Entry<String, Object> entry : reqParam.entrySet()) {
					target.queryParam(entry.getKey(), entry.getValue());
				}
			}

			return target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			log.error("setUpInvocationBuilder error" + e);
		}

		return null;
	}

	protected GatewayConfig getGatewayConfigFromSysParam(String sysParamKey) throws Exception {
		log.info("getGatewayConfigFromSysParam");
		try {

			if (StringUtils.isEmpty(sysParamKey))
				throw new ParameterException("sysParamKey is null");

			TCoreSysparam sysParam = sysParamDao.find(sysParamKey);

			String gwConfig = null;
			if (sysParam != null) {
				gwConfig = sysParam.getSysVal();
			}

			log.info("SysParam: " + gwConfig);
			GatewayConfig config = null;
			if (StringUtils.isNotBlank(gwConfig)) {
				ObjectMapper mapper = new ObjectMapper();
				config = mapper.readValue(gwConfig, GatewayConfig.class);
			}

			return config;

		} catch (Exception e) {
			log.error("getGatewayConfig " + e);
			throw e;
		}

	}

	protected <T extends BaseResponse> T jsonStrToModel(String jsonStrResp, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonStrResp, clazz);
		} catch (IOException ioe) {
			Log.error("jsonStrToModel", ioe);
			try {
				T errorResponse = clazz.getConstructor().newInstance();
				errorResponse.setCode("DESER_ERROR");
				errorResponse
						.setMessage(String.format("Encountered deserialization error while converting jsonStr response "
								+ "to object model - class[%s], jsonStr[%s]", clazz.getName(), jsonStrResp));

				return errorResponse;
			} catch (Exception e) {
				// Should never happen. If happens, means there is programming error,
				// i.e. absence of no-arg constructor for the response model class
				throw new RuntimeException(e);
			}
		}
	}
}
