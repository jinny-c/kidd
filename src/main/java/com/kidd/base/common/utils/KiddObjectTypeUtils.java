package com.kidd.base.common.utils;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Timestamp;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class KiddObjectTypeUtils {

	public static <T> boolean isObject(T t) {
		return isObject(t.getClass());
	}

	public static <T> boolean isObject(Class<T> clazz) {
		return isR(clazz, TypeEnum.OBJECT);
	}

	public static <T> boolean isMap(T t) {
		return isMap(t.getClass());
	}

	public static <T> boolean isMap(Class<T> clazz) {
		return isR(clazz, TypeEnum.MAP);
	}

	public static <T> boolean isList(T t) {
		return isList(t.getClass());
	}

	public static <T> boolean isList(Class<T> clazz) {
		return isR(clazz, TypeEnum.LIST);
	}

	public static <T> boolean isSet(T t) {
		return isSet(t.getClass());
	}

	public static <T> boolean isSet(Class<T> clazz) {
		return isR(clazz, TypeEnum.SET);
	}

	public static <T> boolean isArray(T t) {
		return isArray(t.getClass());
	}

	public static <T> boolean isArray(Class<T> clazz) {
		return clazz.isArray();
	}

	public static <T> boolean isInteger(T t) {
		return isInteger(t.getClass());
	}

	public static <T> boolean isInteger(Class<T> clazz) {
		return isR(clazz, TypeEnum.INTEGER);
	}

	public static <T> boolean isNum(T t) {
		return isNum(t.getClass());
	}

	public static <T> boolean isNum(Class<T> clazz) {
		return isR(clazz, TypeEnum.INTEGER) || isR(clazz, TypeEnum.LONG)
				|| isR(clazz, TypeEnum.SHORT) || isR(clazz, TypeEnum.DOUBLE)
				|| isR(clazz, TypeEnum.FLOAT);
	}

	public static <T> boolean isString(T t) {
		return isString(t.getClass());
	}

	public static <T> boolean isString(Class<T> clazz) {
		return isR(clazz, TypeEnum.STRING);
	}

	public static <T> boolean isBoolean(T t) {
		return isR(t.getClass(), TypeEnum.BOOLEAN);
	}

	public static <T> boolean isEnum(T t) {
		return t.getClass().isEnum() || isR(t.getClass(), TypeEnum.ENUM);
	}

	public static <T> boolean isAnno(T t) {
		return t.getClass().isAnnotation() || isR(t.getClass(), TypeEnum.ENUM);
	}

	public static <T> boolean isInteface(T t) {
		return t.getClass().isInterface();
	}

	public static <T> boolean isBasic(T t) {
		return isBasic(t.getClass());
	}

	public static <T> boolean isBasic(Class<T> clazz) {
		return clazz.isPrimitive();
	}

	public static <T> boolean isJavaBean(T t) {
		return isJavaBean(t.getClass());
	}

	public static <T> boolean isJavaBean(Class<T> clazz) {
		return TypeEnum.isJBean(clazz);
	}

	public static <T> boolean isCollections(T t) {
		return isCollections(t.getClass());
	}

	public static <T> boolean isCollections(Class<T> clazz) {
		return isMap(clazz) || isSet(clazz) || isList(clazz) || isArray(clazz)
				|| isR(clazz, TypeEnum.COLLECTION);
	}

	/**
	 * 判断正确性
	 * 
	 * @param clazz
	 * @param e
	 * @return
	 */
	private static <T> boolean isR(Class<T> clazz, TypeEnum e) {
		return e.getClazz().isAssignableFrom(clazz);
	}

	public static void main(String[] args) {
		Integer flag = 11;
		System.out.println(isJavaBean(flag));
		System.out.println(flag.getClass().isPrimitive());
	}

	private enum TypeEnum {
		/**  **/
		OBJECT(Object.class),
		/**  **/
		INTEGER(Integer.class),
		/**  **/
		BOOLEAN(Boolean.class),
		/**  **/
		MAP(Map.class),
		/**  **/
		STRING(String.class),
		/**  **/
		BYTE(Byte.class),
		/**  **/
		SHORT(Short.class),
		/**  **/
		LONG(Long.class),
		/**  **/
		DOUBLE(Double.class),
		/**  **/
		BIG_DECIMAL(BigDecimal.class),
		/**  **/
		BIG_INTEGER(BigInteger.class),
		/**  **/
		UTIL_DATE(java.util.Date.class),
		/**  **/
		SQL_DATE(java.sql.Date.class),
		/**  **/
		TIMESTAMP(Timestamp.class),
		/**  **/
		ENUM(Enumeration.class),
		/**  **/
		CALENDAR(Calendar.class),
		/**  **/
		LIST(List.class),
		/**  **/
		SET(Set.class),
		/**  **/
		CHARACTER(Character.class),
		/**  **/
		FLOAT(Float.class),
		/**  **/
		ANNOTATION(Annotation.class),
		/**  **/
		COLLECTION(Collection.class),
		/**  **/
		THROWABLE(Throwable.class),
		/**  **/
		TIMEZONE(TimeZone.class),
		/**  **/
		APPENDABLE(Appendable.class),
		/**  **/
		CHARSET(Charset.class),
		/**  **/
		CLOB(Clob.class),

		;

		private Class<?> clazz;

		private TypeEnum(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		static <T> boolean isJBean(Class<T> clazz) {
			if (clazz.isInterface()) {
				return false;
			}
			if (clazz.getName().toLowerCase().startsWith("java.")
					|| clazz.getName().toLowerCase().startsWith("org.")
					|| clazz.getName().toLowerCase().startsWith("javax.")
					|| clazz.getName().toLowerCase().startsWith("com.sun")
					|| clazz.getName().toLowerCase().startsWith("sun.")) {
				return false;
			}
			for (TypeEnum e : values()) {
				if (e != OBJECT && e.getClazz().isAssignableFrom(clazz)) {
					return false;
				}
			}
			return true;
		}
	}
}
