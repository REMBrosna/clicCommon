package com.guudint.clickargo.common.service.impl;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.master.enums.Roles;
import com.vcc.camelone.util.crypto.AESEcbEncryption;

@Service
public class CKEncryptionUtil {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CKEncryptionUtil.class);

	public static String encrypt(String str, String idKey) {

		try {

			String key = StringUtils.rightPad(idKey, 32, "0");
			System.out.println("enc key: " + key);

			return AESEcbEncryption.encrypt(str, key);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return str;

	}

	public static String decrypt(String str, String idKey) {
		try {

			String key = StringUtils.rightPad(idKey, 32, "0");
			System.out.println("key: " + key);
			System.out.println("Decrupt string: " + str);

			return AESEcbEncryption.decrypt(str, key);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return str;
	}

	public static void main(String[] args) throws Exception {

		String key = "CKCTJ231102084009697";
		String encAccnId = encrypt("ALKNAR", key);
		System.out.println("accnId:" + encAccnId);
		System.out.println("roles:" + encrypt(Roles.FF_FINANCE.name() + ":" + Roles.OFFICER.name(), key));
		System.out.println("jobId: " + encrypt("CKCTJ231102084003403", "000"));
		System.out.println("date: " + encrypt("2023-11-07", key));

		System.out.println("decrypted accnId: " + decrypt("71eaf6d86bb89ab753a020ad7f96831c", "CKCTJ231102084009697"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = "2023-12-12";
		System.out.println("Date: " + sdf.parse(dateStr));

	}
}
