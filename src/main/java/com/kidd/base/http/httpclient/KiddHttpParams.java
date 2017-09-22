package com.kidd.base.http.httpclient;

import java.nio.charset.Charset;

import com.kidd.base.common.utils.KiddStringUtils;

class KiddHttpParams {
	/** 通讯URL **/
	private String url;
	/** 通讯编码，默认：UTF-8 **/
	private Charset charset = KiddHttpConstants.DEF_CHARSET;
	/** 连接超时时间,单位:毫秒，默认：10s **/
	private Integer connTimeOut = KiddHttpConstants.DEF_CONN_TIMEOUT;
	/** 响应超时时间,单位:毫秒，默认:10s **/
	private Integer soTimeOut = KiddHttpConstants.DEF_SO_TIMEOUT;
	/** 多线程线程池的最大连接数,默认:5个 **/
	private Integer maxTotal = KiddHttpConstants.DEF_MAX_TOTAL;
	/** 多线程线程池每个域名最大分配连接数,默认:5个 **/
	private Integer maxPerRoute = KiddHttpConstants.DEF_MAX_PER_ROUTE;
	/** 客户端信任证书路径 **/
	private String trustPath;
	/** 客户端信任证书存储密码 **/
	private String trustPwd;
	/** 服务端验证证书路径 **/
	private String keyPath;
	/** 服务端验证证书存储密码 **/
	private String keyStorePwd;
	/** 服务端验证证书密码 **/
	private String keyPwd;
	/** 是否使用连接池，默认:否-不使用连接池 **/
	private boolean isPool = false;
	/** 是否忽略SSL证书认证，默认:忽略-不做SSL认证 **/
	private boolean isIgnoreSSL = true;

	String getUrl() {
		return url;
	}

	void setUrl(String url) {
		this.url = url;
	}

	Charset getCharset() {
		if (charset == null) {
			return KiddHttpConstants.DEF_CHARSET;
		}
		return charset;
	}

	void setCharset(Charset charset) {
		if (charset == null) {
			return;
		}
		this.charset = charset;
	}

	Integer getConnTimeOut() {
		if (isIntErr(connTimeOut)) {
			return KiddHttpConstants.DEF_CONN_TIMEOUT;
		}
		return connTimeOut;
	}

	void setConnTimeOut(Integer connTimeOut) {
		if (isIntErr(connTimeOut)) {
			return;
		}
		this.connTimeOut = connTimeOut;
	}

	Integer getSoTimeOut() {
		if (isIntErr(soTimeOut)) {
			return KiddHttpConstants.DEF_SO_TIMEOUT;
		}
		return soTimeOut;
	}

	void setSoTimeOut(Integer soTimeOut) {
		if (isIntErr(soTimeOut)) {
			return;
		}
		this.soTimeOut = soTimeOut;
	}

	Integer getMaxTotal() {
		if (isIntErr(maxTotal)) {
			return KiddHttpConstants.DEF_MAX_TOTAL;
		}
		return maxTotal;
	}

	void setMaxTotal(Integer maxTotal) {
		if (isIntErr(maxTotal)) {
			return;
		}
		this.maxTotal = maxTotal;
	}

	Integer getMaxPerRoute() {
		if (isIntErr(maxPerRoute)) {
			return KiddHttpConstants.DEF_MAX_PER_ROUTE;
		}
		return maxPerRoute;
	}

	void setMaxPerRoute(Integer maxPerRoute) {
		if (isIntErr(maxPerRoute)) {
			return;
		}
		this.maxPerRoute = maxPerRoute;
	}

	String getTrustPath() {
		return trustPath;
	}

	void setTrustPath(String trustPath) {
		this.trustPath = trustPath;
	}

	String getTrustPwd() {
		return trustPwd;
	}

	void setTrustPwd(String trustPwd) {
		this.trustPwd = trustPwd;
	}

	String getKeyPath() {
		return keyPath;
	}

	void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	String getKeyStorePwd() {
		return keyStorePwd;
	}

	void setKeyStorePwd(String keyStorePwd) {
		this.keyStorePwd = keyStorePwd;
	}

	String getKeyPwd() {
		return keyPwd;
	}

	void setKeyPwd(String keyPwd) {
		this.keyPwd = keyPwd;
	}

	boolean isPool() {
		return isPool;
	}

	void setPool(boolean isPool) {
		this.isPool = isPool;
	}

	boolean isIgnoreSSL() {
		return isIgnoreSSL;
	}

	void setIgnoreSSL(boolean isIgnoreSSL) {
		this.isIgnoreSSL = isIgnoreSSL;
	}

	private boolean isIntErr(Integer v) {
		return v == null || v.intValue() <= 0;
	}

	/**
	 * 判断是否是SSL认证请求
	 * 
	 * @return
	 */
	boolean isHttps() {
		if (KiddStringUtils.isBlank(url)) {
			return false;
		}
		return url.trim().toLowerCase()
				.startsWith(KiddHttpConstants.HTTPS_PREFIX);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KiddHttpParams [url=");
		builder.append(url);
		builder.append(", charset=");
		builder.append(charset);
		builder.append(", connTimeOut=");
		builder.append(connTimeOut);
		builder.append(", soTimeOut=");
		builder.append(soTimeOut);
		builder.append(", maxTotal=");
		builder.append(maxTotal);
		builder.append(", maxPerRoute=");
		builder.append(maxPerRoute);
		builder.append(", trustPath=");
		builder.append(trustPath);
		builder.append(", trustPwd=");
		builder.append(trustPwd);
		builder.append(", keyPath=");
		builder.append(keyPath);
		builder.append(", keyStorePwd=");
		builder.append(keyStorePwd);
		builder.append(", keyPwd=");
		builder.append(keyPwd);
		builder.append(", isPool=");
		builder.append(isPool);
		builder.append(", isIgnoreSSL=");
		builder.append(isIgnoreSSL);
		builder.append("]");
		return builder.toString();
	}
}
