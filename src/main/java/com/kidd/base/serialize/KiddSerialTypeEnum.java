package com.kidd.base.serialize;

import com.kidd.base.enums.KiddSymbolEnum;
import com.kidd.base.utils.KiddStringUtils;


public enum KiddSerialTypeEnum {

	/** json-json序列化 **/
	SERILAL_TYPE_JSON("json", "json序列化", 0),
	/** fastJson-fastJson序列化 **/
	SERILAL_TYPE_FASTJSON("fastJson", "fastJson序列化", 1),
	/** gson-gson序列化 **/
	SERILAL_TYPE_GSON("gson", "gson序列化", 2),

	;

	/** 序列化类型 **/
	private String serilalType;
	/** 序列化类型描述 **/
	private String desc;
	/** 序列化标标识(不可重复) **/
	private int flag;

	private KiddSerialTypeEnum(String serilalType, String desc, int flag) {
		this.serilalType = serilalType;
		this.desc = desc;
		this.flag = flag;
	}

	public String serilalType() {
		return serilalType;
	}

	public String desc() {
		return desc;
	}

	/**
	 * 解析java对象为序列化字符串
	 * 
	 * @param object
	 *            [java对象]
	 * @return[序列化字符串]
	 */
	public String toStr(Object object) {
		if (object == null) {
			return null;
		}
		return serilalObj2Data(object, this);
	}

	/**
	 * 解析序列化字符串为java对象
	 * 
	 * @param data
	 *            [序列化字符串]
	 * @param clazz
	 * @return
	 */
	public <T> T toObj(String data, Class<T> clazz) {
		if (KiddStringUtils.isBlank(data) || clazz == null) {
			return null;
		}
		return serilalData2Obj(clazz, data, this);
	}

	/**
	 * 解析java对象为序列化字符串
	 * 
	 * @param object
	 * @param e
	 * @return
	 */
	public static String serilalObj2Data(Object object, KiddSerialTypeEnum e) {
		if (object == null || e == null) {
			return null;
		}
		switch (e.flag) {
		case 0:
			return KiddJsonUtils.toStr(object);
		case 1:
			return KiddFastJsonUtils.toStr(object);
		case 2:
			//TODO 待实现
			return KiddSymbolEnum.Blank.symbol();
		default:
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 解析序列化字符串为java对象
	 * 
	 * @param clazz
	 * @param data
	 * @param e
	 * @return
	 */
	public static <T> T serilalData2Obj(Class<T> clazz, String data,
			KiddSerialTypeEnum e) {
		if (KiddStringUtils.isBlank(data) || clazz == null) {
			return null;
		}
		switch (e.flag) {
		case 0:
			return KiddJsonUtils.toObj(data, clazz);
		case 1:
			return KiddFastJsonUtils.toObj(data, clazz);
		case 2:
			//TODO
			return null;
		default:
			return null;
		}
	}

	public boolean isGson() {
		return this == SERILAL_TYPE_GSON;
	}

	public boolean isFastJson() {
		return this == SERILAL_TYPE_FASTJSON;
	}

	public boolean isJson() {
		return this == SERILAL_TYPE_JSON;
	}
	
	/**
	 * 转换
	 * 
	 * @param serilalType
	 * @return
	 */
	public static KiddSerialTypeEnum convert2Self(String serilalType) {
		if (KiddStringUtils.isBlank(serilalType)) {
			return null;
		}
		for (KiddSerialTypeEnum e : values()) {
			if (e.serilalType().equals(serilalType)) {
				return e;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return KiddStringUtils.join("name=", name(),
				KiddSymbolEnum.Comma.symbol(), " serilalType=", serilalType,
				" desc=", desc);
	}
}
