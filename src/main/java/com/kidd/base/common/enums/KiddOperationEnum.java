package com.kidd.base.common.enums;

import com.kidd.base.common.utils.KiddStringUtils;

public enum KiddOperationEnum {

	operationg_insert("insert", "数据新增"),
	operationg_update("update", "数据修改"),
	operationg_delete("delete", "数据删除"),
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

	KiddOperationEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public static boolean isExsit(String key) {
		if (KiddStringUtils.isBlank(key)) {
			return false;
		}
		for (KiddOperationEnum e : values()) {
			if (e.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	public static KiddOperationEnum convert2Self(String key) {
		if (KiddStringUtils.isBlank(key)) {
			return null;
		}
		for (KiddOperationEnum e : values()) {
			if (e.key.equals(key)) {
				return e;
			}
		}
		return null;
	}

}
