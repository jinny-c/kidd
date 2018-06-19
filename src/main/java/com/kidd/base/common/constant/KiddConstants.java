package com.kidd.base.common.constant;

public class KiddConstants {
	public static final String MSG_TYPE_TEXT = "text";
	public static final String MSG_TYPE_CLICK = "CLICK";
	public static final String MSG_TYPE_LOCATION = "LOCATION";
	public static final String MSG_TYPE_SUBSCRIBE = "subscribe";
	public static final String DEFAULT_PASSWORD = "111111";
	public final static String WX_OAUTH2_BASE_SCOPE = "snsapi_base";
	public final static String WX_OAUTH2_USERINFO_SCOPE = "snsapi_userinfo";
	public final static String WX_PUB_NUM_TOKEN = "handpay";
	public final static Integer DEFAULT_PAGE_NO = 1;
	public final static Integer DEFAULT_PAGE_SIZE = 10;
	public final static String CURRENT_USER = "userEntity";
	public final static String OAUTH2_CODE = "code";
	public final static String PUB_NO_ID = "pubId";
	public final static String SCAN_SHARE_CODE_URI = "/scanShareCode.htm";

	/**
	 * 通讯编码，默认:UTF-8
	 */
	public final static String CHARSET_DEF = "UTF-8";
	
	public static String ATTR_REQUEST_DATA = "requestData";
	/**
	 * HTTP ContentType 取值 - 文本
	 */
	public final static String CONTENT_TYPE_TEXT = "text/plain; charset=UTF-8";
	/**
	 * HTTP ContentType 取值 - 二进制
	 */
	public final static String CONTENT_TYPE_BINARY = "application/octet-stream";
	
	public static final String X_REQUESTED_WITH = "x-requested-with";
	public static final String FIELD_USER_AGENT = "User-Agent";
}