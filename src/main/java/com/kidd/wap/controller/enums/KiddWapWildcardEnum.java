package com.kidd.wap.controller.enums;

import com.kidd.base.common.utils.KiddStringUtils;

public enum KiddWapWildcardEnum {

	wap_wildcard_imageCode("imageCode", "图形验证码"),
	wap_wildcard_verifyCode("verifyCode", "手机验证码"),
	;

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	KiddWapWildcardEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public static boolean isExsit(String key) {
		if (KiddStringUtils.isBlank(key)) {
			return false;
		}
		for (KiddWapWildcardEnum e : values()) {
			if (e.key.equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isImageCode(String key) {
		return wap_wildcard_imageCode.key.equals(key);
	}
	
	public static boolean isVerifyCode(String key) {
		return wap_wildcard_verifyCode.key.equals(key);
	}
}
