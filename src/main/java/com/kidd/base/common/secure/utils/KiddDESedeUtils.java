package com.kidd.base.common.secure.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidd.base.common.enums.KiddSymbolEnum;


/**
 * 3DES算法工具类
 * 
 * @history
 */
public class KiddDESedeUtils {

	private static Logger logger = LoggerFactory.getLogger(KiddDESedeUtils.class);

	private static final String KEY_ALGORITEM = "DESede";

	private static final String CIPHER_ALGORITEM = "DESede/ECB/PKCS5Padding";
	
	private static final String CHARSET_UTF8 = "UTF-8";

	private static Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITEM);
		return skf.generateSecret(dks);
	}

	/**
	 * 加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] encr(byte[] key, byte[] data) {
		try {
			Key k = toKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITEM);
			cipher.init(Cipher.ENCRYPT_MODE, k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("3DES encr exception", e);
			return null;
		}
	}
	public static String doEncr(String key, String data) {
		try {
			return KiddHexUtil.byte2hex(encr(KiddHexUtil.hex2byte(key), data.getBytes(CHARSET_UTF8)));
		} catch (Exception e) {
			logger.error("doEncr exception,key={},data={},exception={}", key, data, e);
		}
		return KiddSymbolEnum.Blank.symbol();
	}
	/**
	 * 解密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] decr(byte[] key, byte[] data) {
		try {
			Key k = toKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITEM);
			cipher.init(Cipher.DECRYPT_MODE, k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("3DES decr exception", e);
			return null;
		}
	}
	public static String doDecr(String key, String data) {
		try {
			return new String(decr(KiddHexUtil.hex2byte(key), KiddHexUtil.hex2byte(data)),CHARSET_UTF8);
		} catch (Exception e) {
			logger.error("doEncr exception,key={},data={},exception={}", key, data, e);
		}
		return KiddSymbolEnum.Blank.symbol();
	}
	
	public static void main(String[] args) throws Exception {
		//3des 加解密，key[48]位
		String desKey = "50d43ea2a4e6678650d43ea2a4e6678650d43ea2a4e66786";
		//解密
		String encryStr = doEncr(desKey, "123123");
		System.out.println(encryStr);
		//解密
		String decrStr = doDecr(desKey, encryStr);
		System.out.println(decrStr);
		//String fkey = "41FDD9B50B977DB341FDD9B50B977DB341FDD9B50B977DB3";
	}
}
