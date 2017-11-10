package com.kidd.wap.controller.enums;

import com.kidd.base.common.utils.KiddStringUtils;

public enum KiddWapWildcardEnum {

	wap_wildcard_imageCode("imageCode", "图形验证码"),
	wap_wildcard_verifyCode("verifyCode", "手机验证码"),
	;

	private String key;
	private String desc;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	KiddWapWildcardEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
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
	
	public static KiddWapWildcardEnum convert2Self(String key) {
		if (KiddStringUtils.isBlank(key)) {
			return null;
		}
		for (KiddWapWildcardEnum e : values()) {
			if (e.key.equals(key)) {
				return e;
			}
		}
		return null;
	}

}
