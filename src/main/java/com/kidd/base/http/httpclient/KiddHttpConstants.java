package com.kidd.base.http.httpclient;
import java.nio.charset.Charset;

class KiddHttpConstants {
	
	/** 连接超时时间，默认：10秒 **/
	static final Integer DEF_CONN_TIMEOUT = 10 * 1000;
	/** 响应超时时间，默认：10秒 **/
	static final Integer DEF_SO_TIMEOUT = 10 * 1000;
	/** 通讯编码，默认:UTF-8 **/
	static final Charset DEF_CHARSET = Charset.forName("UTF-8");
	/** 多线程线程池的最大连接数,默认:5个 */
	static final Integer DEF_MAX_TOTAL = 5;
	/** 多线程线程池每个域名最大分配连接数,默认:5个 **/
	static final Integer DEF_MAX_PER_ROUTE = 5;
	/** https前缀 **/
	static final String HTTPS_PREFIX = "https";
	/** http前缀 **/
	static final String HTTP_PREFIX = "http";
	
	/** 业务参数 **/
	static String HTTP_BIZ_CONTENT = "biz_content";
}
