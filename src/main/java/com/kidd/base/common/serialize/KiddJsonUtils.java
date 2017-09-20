package com.kidd.base.common.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class KiddJsonUtils {

	/**
	 * 解析java对象为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static <T> String toStr(T object) {
		return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 解析json字符串为java对象
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> T toObj(String data, Class<T> clazz) {
		return JSON.parseObject(data, clazz);
	}

}
