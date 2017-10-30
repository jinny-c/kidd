package com.kidd.base.common.secure.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 16进制工具类
 * 
 * @history
 */
public class KiddHexUtil {

	/**
	 * byte二进制 转 hex 十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder hexStr = new StringBuilder();
		for (int i = 0; i < bytes.length; ++i) {
			hexStr.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF))
					.substring(1).toUpperCase());
		}
		return hexStr.toString();
	}

	/**
	 * hex十六进制 转 byte二进制
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2byte(String hex) {
		if (StringUtils.isBlank(hex)) {
			return null;
		}
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
					16);
		}
		return bts;
	}
}
