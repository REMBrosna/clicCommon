package com.guudint.clickargo.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CkUtil {

	private static final int IDLEN = 35;
	
	/**
	 * Generate id. id= appType + System.nanoTime()
	 * 
	 * @param appType
	 * @return
	 */
	synchronized public static String generateId(String prefix) {
		String genId = prefix + generateId();
		if (genId.length() > IDLEN) {
			genId = genId.substring(0, IDLEN);
		}
		return genId;
	}

	synchronized public static String generateIdSynch(String prefix) {
		String genId = prefix + generateRecordId();
		if (genId.length() > IDLEN) {
			genId = genId.substring(0, IDLEN);
		}
		return genId;
	}

	synchronized public static String generateRecordId() {
		return StringUtils.EMPTY + System.nanoTime();
	}

	synchronized public static String generateId() {
		int ranNo = ThreadLocalRandom.current().nextInt(0, 99999 + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMddkkmm");
		String genId = sdf.format(Calendar.getInstance().getTime()) + String.format("%05d", ranNo);
		return genId;
	}

	// PORTEDI-563
	public static String generateId(String prefix, String addPrefix) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String yyyyMMdd = sdf.format(new Date());
		int ranNo = new Random().nextInt(99999);
		return prefix + addPrefix + yyyyMMdd + ranNo;
	}

	public static String generateToken() {
		Date dateNow = new Date();
		int random = (int) (Math.random() * 1000);
		if (random < 100) {
			random += 100;
		}
		String ret = new SimpleDateFormat("yyMMdd-HHmmss-SSS").format(dateNow) + random;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> jsonToMap(String jsonStr) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(jsonStr, Map.class);
		return map;
	}
	
	public static String getComputerName()
	{
		try {
		    Map<String, String> env = System.getenv();
		    if (env.containsKey("COMPUTERNAME"))
		        return env.get("COMPUTERNAME");
		    else if (env.containsKey("HOSTNAME"))
		        return env.get("HOSTNAME");
			else
				return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
	        return "Unknown Computer";
		}
	}
}
