package com.kidd.base.http.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import org.apache.http.util.Args;


/**
 * SSL证书工具类
 * 
 * @history
 */
class KiddSSLCertConverter {

	/**
	 * 证书文件转keyStore
	 * 
	 * @param file
	 *            [证书文件]
	 * @param storePwd
	 *            [文件存储密码]
	 * @return
	 * @throws Exception
	 */
	static KeyStore toKeyStore(File file, char[] storePwd) throws Exception {
		Args.notNull(file, "KeyStore file");
		final KeyStore keyStore = KeyStore.getInstance(KeyStore
				.getDefaultType());
		final FileInputStream in = new FileInputStream(file);
		try {
			keyStore.load(in, storePwd);
		} finally {
			if (null != in) {
				in.close();
			}
		}
		return keyStore;
	}

	/**
	 * 无存储密码证书文件转keyStore
	 * 
	 * @param file
	 *            [证书文件]
	 * @return
	 * @throws Exception
	 */
	static KeyStore toKeyStore(File file) throws Exception {
		return toKeyStore(file, null);
	}

	/**
	 * 证书文件转keyStore
	 * 
	 * @param filePath
	 *            [证书文件路径]
	 * @param storePwd
	 *            [文件存储密码]
	 * @return
	 * @throws Exception
	 */
	static KeyStore toKeyStore(String filePath, char[] storePwd)
			throws Exception {
		return toKeyStore(new File(filePath), storePwd);
	}

	/**
	 * 无存储密码证书文件转keyStore
	 * 
	 * @param filePath
	 *            [证书文件路径]
	 * @return
	 * @throws Exception
	 */
	static KeyStore toKeyStore(String filePath) throws Exception {
		return toKeyStore(filePath, null);
	}
}
