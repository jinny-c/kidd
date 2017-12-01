package com.kidd.base.common.serialize;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class KiddFastJsonUtils {

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
	
	public static <T> List<T> toListObj(String data, Class<T> clazz) {
		return JSON.parseArray(data, clazz);
	}

}
