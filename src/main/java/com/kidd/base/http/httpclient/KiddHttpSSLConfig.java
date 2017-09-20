package com.kidd.base.http.httpclient;

import java.io.File;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.Args;

import com.kidd.base.utils.KiddStringUtils;
class KiddHttpSSLConfig {

	/**
	 * 忽略SSL认证
	 * 
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builderIgnoreSSL() throws Exception {
		return new SSLConnectionSocketFactory(SSLContexts.custom()
				.loadTrustMaterial(new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build(), new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}

	/**
	 * SSL单向认证，只做客户端验证[也就是信任证书认证]
	 * 
	 * @param trustStore
	 *            [客户端证书]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builderOnlyTrust(KeyStore trustStore)
			throws Exception {
		return new SSLConnectionSocketFactory(SSLContexts.custom()
				.loadTrustMaterial(trustStore, null).build());
	}

	/**
	 * SSL单向认证，只做客户端验证[也就是信任证书认证]
	 * 
	 * @param filePath
	 *            [客户端证书路径]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builderOnlyTrust(String filePath)
			throws Exception {
		Args.notBlank(filePath, "trust store path");
		return builderOnlyTrust(filePath, null);
	}

	/**
	 * SSL单向认证，只做客户端验证[也就是信任证书认证]
	 * 
	 * @param filePath
	 *            [客户端证书路径]
	 * @param pwd
	 *            [证书存储密码]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builderOnlyTrust(String filePath,
			String pwd) throws Exception {
		Args.notBlank(filePath, "trust store path");
		return builderOnlyTrust(KiddSSLCertConverter.toKeyStore(filePath,
				toCharAry(pwd)));
	}

	/**
	 * SSL单向认证，只做客户端验证[也就是信任证书认证]
	 * 
	 * @param file
	 *            [客户端证书]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builderOnlyTrust(File file)
			throws Exception {
		Args.notNull(file, "trust store file");
		return builderOnlyTrust(file, null);
	}

	/**
	 * SSL单向认证，只做客户端验证[也就是信任证书认证]
	 * 
	 * @param file
	 *            [客户端证书]
	 * @param pwd
	 *            [证书存储密码]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builderOnlyTrust(File file, String pwd)
			throws Exception {
		return builderOnlyTrust(KiddSSLCertConverter.toKeyStore(file,
				toCharAry(pwd)));
	}

	/**
	 * SSL双向认证
	 * 
	 * @param trustStore
	 *            [客户端证书]
	 * @param keyStore
	 *            [服务端证书]
	 * @param keyPwd
	 *            [服务端证书密码]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builder(KeyStore trustStore,
			KeyStore keyStore, char[] keyPwd) throws Exception {
		Args.notNull(trustStore, "trust store");
		Args.notNull(keyStore, "key store");
		return new SSLConnectionSocketFactory(SSLContexts.custom()
				.loadTrustMaterial(trustStore, null)
				.loadKeyMaterial(keyStore, keyPwd).build());
	}

	/**
	 * SSL双向认证
	 * 
	 * @param trustPath
	 *            [客户端证书路径]
	 * @param trustPwd
	 *            [客户端证书存储密码]
	 * @param keyPath
	 *            [服务端证书路径]
	 * @param keyStorePwd
	 *            [服务端证书存储密码]
	 * @param keyPwd
	 *            [服务端证书密码]
	 * @return
	 * @throws Exception
	 */
	static SSLConnectionSocketFactory builder(String trustPath,
			String trustPwd, String keyPath, String keyStorePwd, String keyPwd)
			throws Exception {
		Args.notBlank(trustPath, "trust store path");
		Args.notBlank(keyPath, "key store path");
		return new SSLConnectionSocketFactory(SSLContexts
				.custom()
				.loadTrustMaterial(
						KiddSSLCertConverter.toKeyStore(trustPath,
								toCharAry(trustPwd)), null)
				.loadKeyMaterial(
						KiddSSLCertConverter.toKeyStore(keyPath,
								toCharAry(keyStorePwd)), toCharAry(keyPwd))
				.build());
	}

	private static char[] toCharAry(String pwd) {
		if (KiddStringUtils.isBlank(pwd)) {
			return null;
		}
		return pwd.toCharArray();
	}
}
