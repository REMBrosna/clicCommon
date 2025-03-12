package com.guudint.clickargo.external.services;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

public class JerseyHttpClientFactory {
	static Client getJerseyHTTPSClient(String sslTsl, FileHandler fh) throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext sslContext = getSslContext(sslTsl);
		HostnameVerifier allHostsValid = new NoOpHostnameVerifier();

		return ClientBuilder.newBuilder().register(logging(fh)).sslContext(sslContext).hostnameVerifier(allHostsValid)
				.build();
	}
	
	static Client getJerseyHTTPSClient(String sslTsl, ClientConfig config) throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext sslContext = getSslContext(sslTsl);
		HostnameVerifier allHostsValid = new NoOpHostnameVerifier();

		return ClientBuilder.newBuilder().withConfig(config).sslContext(sslContext).hostnameVerifier(allHostsValid)
				.build();
	}

	private static LoggingFeature logging(FileHandler fh) {
		try {
			Logger logger = Logger.getLogger("JerseyHttpClientFactory");
			logger.addHandler(fh);
			return new LoggingFeature(logger, Level.INFO, null, null);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private static SSLContext getSslContext(String sslTsl) throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance(sslTsl);

		KeyManager[] keyManagers = null;
		TrustManager[] trustManager = { new NoOpTrustManager() };
		SecureRandom secureRandom = new SecureRandom();

		sslContext.init(keyManagers, trustManager, secureRandom);

		return sslContext;
	}
}
