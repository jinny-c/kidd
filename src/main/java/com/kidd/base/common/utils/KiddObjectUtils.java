package com.kidd.base.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.core.convert.ConversionException;

import com.kidd.base.common.enums.KiddSymbolEnum;

public class KiddObjectUtils {

	
	/**
	 * 对象拷贝
	 * 
	 * @param dest
	 * 目标、目的
	 * 
	 * @param orig
	 * 最初、原始
	 */
	public static void copyProperty(Object dest, Object orig) {
		if (orig == null) {
			throw new ClassConversionException("the orig is null");
		}

		if (dest == null) {
			throw new ClassConversionException("the dest is null");
		}
		Class<?> clazz = dest.getClass();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			Method writer = null;
			Method reader = null;
			Object value = null;

			Method orgReader = null;
			Class<?> orgClazz = orig.getClass();
			for (PropertyDescriptor pd : pds) {
				try {
					reader = pd.getReadMethod();
					if (reader == null) {
						continue;
					}
					orgReader = orgClazz.getMethod(reader.getName(),
							reader.getParameterTypes());

					if (orgReader == null) {
						continue;
					}
					value = orgReader.invoke(orig);
					if (isObjNull(value)) {
						continue;
					}
					writer = pd.getWriteMethod();
					if (writer == null) {
						continue;
					}
					writer.invoke(dest, value);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			throw new ClassConversionException("copy orig property to dest fail", e);
		}
	}

	/**
	 * 拷贝属性 并返回新对象
	 * 
	 * @param destClazz
	 * @param orig
	 * @return
	 */
	public static <T> T copyProperty(Class<? extends T> destClazz, Object orig) {
		T dest = null;
		try {
			dest = destClazz.newInstance();
		} catch (Exception e) {
			throw new ClassConversionException("the dest clazz is error",e);
		}
		copyProperty(dest, orig);
		if (dest != null) {
			return dest;
		}
		throw new ClassConversionException("copy orig property to dest fail");
		//return dest;
	}

	public static <D, S> Collection<D> copyPropertyCollections(
			Collection<D> dest, Collection<S> orig,Class<D> destClazz) {
		if (orig == null || orig.isEmpty()) {
			throw new ClassConversionException("the collection orig is null");
		}

		if (dest == null) {
			throw new ClassConversionException("the collection dest is null");
		}
		if (!dest.isEmpty()) {
			dest.clear();
		}
		for (S obj : orig) {
			dest.add(copyProperty(destClazz, obj));
		}
		return dest;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isObjNull(Object obj) {
		if (obj == null) {
			return true;
		}
		if (KiddObjectTypeUtils.isString(obj)) {
			String strObj = (String) obj;
			return KiddStringUtils.isBlank(strObj);
		}
		if (KiddObjectTypeUtils.isList(obj) || KiddObjectTypeUtils.isSet(obj)) {
			Collection coll = (Collection) obj;
			return coll.isEmpty();
		}
		if (KiddObjectTypeUtils.isMap(obj)) {
			Map map = (Map) obj;
			return map.isEmpty();
		}
		if (KiddObjectTypeUtils.isArray(obj)) {
			Object[] array = (Object[]) obj;
			return array.length == 0;
		}
		return false;
	}

	/**
	 * 判断为空或为0
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNullOrZeroNum(Integer num) {
		return num == null || num == 0;
	}

	/**
	 * 判断为空或为0
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNullOrZeroNum(Long num) {
		return num == null || num == 0;
	}

	/**
	 * 把map转为String key separator1 value separator2 key separator1 value
	 * 
	 * @param map
	 * @param separator1
	 * @param separator2
	 * @return
	 */
	public static String mapToString(Map<String, String> map,
			String separator1, String separator2) {
		if (map == null) {
			return KiddSymbolEnum.Blank.symbol();
		}
		if (KiddStringUtils.isBlank(separator1)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		if (KiddStringUtils.isBlank(separator2)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		StringBuilder sb = new StringBuilder();
		Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String value = map.get(key);
			sb.append(key).append(separator1).append(value).append(separator2);
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - separator2.length());
		}
		return sb.toString();
	}

	public static String toStr(Object obj) {
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}
	
	@SuppressWarnings("serial")
	static class ClassConversionException extends ConversionException {
		public ClassConversionException(String message, Exception ex) {
			super(message, ex);
		}
		public ClassConversionException(String message) {
			super(message);
		}
	}
}
