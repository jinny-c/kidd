package com.kidd.base.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.kidd.base.common.enums.KiddSymbolEnum;

public class KiddStringUtils {

	/**
	 * 非空字符串去除空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimStr(String str) {
		if (isBlank(str)) {
			return str;
		}
		return str.trim();
	}

	/**
	 * 字符串分割为数组
	 * 
	 * @return
	 */
	public static String[] splitToArray(String source, KiddSymbolEnum symbol) {
		if (isBlank(source)) {
			return null;
		}
		return StringUtils.split(source, symbol.symbol());
	}

	/**
	 * 合并字符
	 * 
	 * @return
	 */
	public static String join(Object... args) {
		return join(null, args);
	}

	/**
	 * 合并字符
	 * 
	 */
	public static String join(KiddSymbolEnum symbol, Object... args) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (Object obj : args) {
			if (count > 0 && symbol != null && isNotBlank(symbol.symbol())) {
				sb.append(symbol.symbol());
			}
			sb.append(obj != null ? obj : KiddSymbolEnum.Blank.symbol());
			count++;
		}
		return sb.toString();
	}

	/**
	 * 判断非空
	 * 
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 判断空
	 * 
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * 占位符日志输出 MicroStringUtils.replace("请求：txnSeqNo={},txnDate={}", "12342342",
	 * "20160323"))
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public static String replace(String message, String... args) {
		Pattern innerPattern = Pattern.compile("\\{[^{}]*\\}");
		Matcher matcher = innerPattern.matcher(message);
		StringBuffer result = new StringBuffer();
		int index = 0;
		while (matcher.find()) {
			matcher.appendReplacement(result,
					index < args.length ? (null == args[index] ? "null"
							: args[index]) : KiddSymbolEnum.Blank.symbol());
			index++;
		}
		matcher.appendTail(result);
		return result.toString();
	}
}