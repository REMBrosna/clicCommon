package com.guudint.clickargo.validator;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.guudint.clickargo.common.model.ValidationError;



public class CkFieldValidatorUtils {

	// Static attributes
	///////////////////
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_PATTERN = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
	private static final String URL_PATTERN = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?.*";
	// private static final String ALPHA_NUMERIC = "^[a-zA-Z0-9]+$";
	// private static final String ALPHA_NUMERIC_WITH_SPACES = "^[a-zA-Z0-9\\s]+$";

	public enum ErrorType {
		MANDATORY_ERR, STRING_ERR, NUMBER_ERR, LENGTH_ERR, PATTERN_ERR
	}

	// Mandatory Validation
	public static ValidationError rejectIfEmptyOrWhitespace(String value, String fieldName) {
		if (StringUtils.isBlank(value)) {
			return new ValidationError(fieldName, ErrorType.MANDATORY_ERR.name(), fieldName + " is Required");
		}
		return null;
	}

	public static ValidationError rejectIfEmptyOrWhitespace(String value, String fieldName, String fieldLabel) {
		if (StringUtils.isBlank(value)) {
			return new ValidationError(fieldName, ErrorType.MANDATORY_ERR.name(), fieldLabel + " is Required");
		}
		return null;
	}

	// isString Validation
	public static ValidationError isString(String value, String fieldName) {
		if (!StringUtils.isAlpha(value)) {
			return new ValidationError(fieldName, ErrorType.STRING_ERR.name(), fieldName + " is not String");
		}
		return null;
	}

	public static ValidationError isString(String value, String fieldName, String fieldLabel) {
		if (!StringUtils.isAlpha(value)) {
			return new ValidationError(fieldName, ErrorType.STRING_ERR.name(), fieldLabel + " is not String");
		}
		return null;
	}

	// isNumeric Validation
	public static ValidationError isNumeric(String value, String fieldName) {
		if (value == null) {
			return new ValidationError(fieldName, ErrorType.MANDATORY_ERR.name(), fieldName + " is Required");
		}
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			return new ValidationError(fieldName, ErrorType.NUMBER_ERR.name(), fieldName + " is not Numeric");
		}
		return null;
	}

	public static ValidationError isNumeric(String value, String fieldName, String fieldLabel) {
		if (value == null) {
			return new ValidationError(fieldName, ErrorType.MANDATORY_ERR.name(), fieldLabel + " is Required");
		}
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			return new ValidationError(fieldName, ErrorType.NUMBER_ERR.name(), fieldLabel + " is not Numeric");
		}
		return null;
	}

	// Email Validation
	public static ValidationError rejectEmail(String value, String fieldName) {
		return commonPattern(value, fieldName, EMAIL_PATTERN);
	}

	public static ValidationError rejectEmail(String value, String fieldName, String fieldLabel) {
		return commonPattern(value, fieldName, fieldLabel, EMAIL_PATTERN);
	}

	// Phone Validation
	public static ValidationError rejectPhone(String value, String fieldName) {
		return commonPattern(value, fieldName, PHONE_PATTERN);
	}

	public static ValidationError rejectPhone(String value, String fieldName, String fieldLabel) {
		return commonPattern(value, fieldName, fieldLabel, PHONE_PATTERN);
	}

	// URL Validation
	public static ValidationError rejectUrl(String value, String fieldName) {
		return commonPattern(value, fieldName, URL_PATTERN);
	}

	public static ValidationError rejectUrl(String value, String fieldName, String fieldLabel) {
		return commonPattern(value, fieldName, fieldLabel, URL_PATTERN);
	}

	// length Validation
	public static ValidationError rejectIfLengthExceeds(String value, String fieldName, int length) {
		if (StringUtils.isNotBlank(value)) {
			if (value.getBytes(StandardCharsets.UTF_8).length > length) {
				return new ValidationError(fieldName, ErrorType.LENGTH_ERR.name(), fieldName + " Invalid Length");
			}
		}
		return null;
	}

	public static ValidationError rejectIfLengthExceeds(String value, String fieldName, String fieldLabel, int length) {
		if (StringUtils.isNotBlank(value)) {
			if (value.getBytes(StandardCharsets.UTF_8).length > length) {
				return new ValidationError(fieldName, ErrorType.LENGTH_ERR.name(), fieldLabel + " Invalid Length");
			}
		}
		return null;
	}

	// reject if length not in between
	public static ValidationError rejectIfLenghtNotBetween(String value, String fieldName, int minLength,
			int maxLengh) {
		if (StringUtils.isNotBlank(value)) {
			if (value.getBytes(StandardCharsets.UTF_8).length < minLength
					|| value.getBytes(StandardCharsets.UTF_8).length > maxLengh) {
				return new ValidationError(fieldName, ErrorType.LENGTH_ERR.name(), fieldName + " Invalid Length");
			}
		}
		return null;
	}

	public static ValidationError rejectIfLenghtNotBetween(String value, String fieldName, String fieldLabel,
			int minLength, int maxLengh) {
		if (StringUtils.isNotBlank(value)) {
			if (value.getBytes(StandardCharsets.UTF_8).length < minLength
					|| value.getBytes(StandardCharsets.UTF_8).length > maxLengh) {
				return new ValidationError(fieldName, ErrorType.LENGTH_ERR.name(), fieldLabel + " Invalid Length");
			}
		}
		return null;
	}

	// Helper Methods
	private static ValidationError commonPattern(String value, String fieldName, String customPartern) {
		if (!StringUtils.isEmpty(value)) {
			Pattern pattern = Pattern.compile(customPartern);
			Matcher matcher = pattern.matcher(value);
			if (!matcher.matches()) {
				return new ValidationError(fieldName, ErrorType.PATTERN_ERR.name(), fieldName + " is  not Valid");
			}
		}
		return null;
	}

	private static ValidationError commonPattern(String value, String fieldName, String fieldLabel,
			String customPartern) {
		if (!StringUtils.isEmpty(value)) {
			Pattern pattern = Pattern.compile(customPartern);
			Matcher matcher = pattern.matcher(value);
			if (!matcher.matches()) {
				return new ValidationError(fieldName, ErrorType.PATTERN_ERR.name(), fieldLabel + " is  not Valid");
			}
		}
		return null;
	}

	/*
	 * public ValidationError rejectIfFileSizeExceeds(String errorCode, String
	 * fieldName, String fieldDescription, String errorType, String
	 * errorDescription, MultipartFile fieldValue, int sizeInMB) { try { if
	 * (!fieldValue.isEmpty() && null != fieldValue.getBytes() &&
	 * fieldValue.getBytes().length > 0) { double size =
	 * (double)fieldValue.getSize() / 1048576.0D; if (size > (double)sizeInMB) {
	 * return new ValidationError(errorCode, fieldName, fieldDescription, errorType,
	 * errorDescription); }
	 * 
	 * } } catch (Exception e) { log.error("rejectIfFileSizeExceeds : ", e); }
	 * return null; }
	 * 
	 * public ValidationError rejectIfFileSizeIFNotBetween(String errorCode, String
	 * fieldName, String fieldDescription, String errorType, String
	 * errorDescription, MultipartFile fieldValue, double minSizeInKB, double
	 * maxSizeInMB) { try { if (!fieldValue.isEmpty() && null !=
	 * fieldValue.getBytes() && fieldValue.getBytes().length > 0) { double size =
	 * (double)fieldValue.getSize() / 1048576.0D; if (size > maxSizeInMB) { return
	 * new ValidationError(errorCode, fieldName, fieldDescription, errorType,
	 * errorDescription); }
	 * 
	 * if ((double)(fieldValue.getSize() / 1024L) < minSizeInKB) { return new
	 * ValidationError(errorCode, fieldName, fieldDescription, errorType,
	 * errorDescription); } } } catch (Exception e) {
	 * log.error("rejectIfFileSizeIFNotBetween : ", e); } return null; }
	 */

}
