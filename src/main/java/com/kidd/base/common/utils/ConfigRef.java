package com.kidd.base.common.utils;

public final class ConfigRef {
	/** microWap.properties*/
	public final static String NO_CARD_PAY_GATEWAY_URL = KiddWapConfigurer.getContextProperty("nocardpay.gateway.url");
	public final static String NO_CARD_PAY_IMAGES_PATH = KiddWapConfigurer.getContextProperty("nocardpay.images.path");

	/** WAP应用配置 */
	public final static String LOGIN_URI = PropertiesUtil.getProperty("login.uri");
	public final static String USER_INFO_URL = PropertiesUtil.getProperty("user.info.url");
	public final static String SHARE_URL = PropertiesUtil.getProperty("share.url");
	public final static String NO_CARD_PAY_BACK_URL = PropertiesUtil.getProperty("nocardpay.backurl");
	public final static String NO_CARD_PAY_FRONT_URL = PropertiesUtil.getProperty("nocardpay.fronturl");

	/** 扫码支付微信公众号配置 */
	public final static String WX_APP_ID_SCAN_PAY = PropertiesUtil.getProperty("scanpay.wx.appid");
	public final static String WX_APP_SECRET_SCAN_PAY = PropertiesUtil.getProperty("scanpay.wx.appsecret");


	/** 微信接口配置 */
	//网页授权
	public final static String WX_OAUTH2_URI = PropertiesUtil.getProperty("wx.oauth2.uri");
	//微信网页授权凭证
	public final static String WX_OAUTH2_TOKEN_URI = PropertiesUtil.getProperty("wx.oauth2.token.uri");
	//微信浏览器提示
	public final static String WX_FOLLOW_URI = PropertiesUtil.getProperty("wx.follow.uri");
	//微信用户信息接口
	public final static String WX_USER_INFO_URI = PropertiesUtil.getProperty("wx.userinfo.uri");
	//调用微信接口凭证
	public final static String WX_TOKEN_URI = PropertiesUtil.getProperty("wx.token.uri");
	//调用微信jsapi接口凭证
	public final static String WX_TICKET_URI = PropertiesUtil.getProperty("wx.ticket.uri");
	//发送消息--客服消息接口
	public final static String WX_MSG_KF_URI = PropertiesUtil.getProperty("wx.msg.kf.uri");
	//发送消息--模板消息接口
	public final static String WX_MSG_TMP_URI = PropertiesUtil.getProperty("wx.msg.tmp.uri");
	//创建微信公众号菜单
	public final static String WX_MENU_CREATE_URI = PropertiesUtil.getProperty("wx.menu.create.uri");
	//长链接转短链接
	public final static String WX_SHORT_TOKEN = PropertiesUtil.getProperty("wx.short.uri");

	/** 支付宝配置 */
	public final static String AP_OAUTH2_URI = PropertiesUtil.getProperty("ap.oauth2.uri");
	public final static String AP_GATEWAY_URI = PropertiesUtil.getProperty("ap.gateway.api");
	public final static String AP_APP_ID = PropertiesUtil.getProperty("ap.appid");
	public final static String AP_MER_PRIVATE_KEY = PropertiesUtil.getProperty("ap.mer.privatekey");
	public final static String AP_MER_PUBLIC_KEY = PropertiesUtil.getProperty("ap.mer.publickey");
	public final static String AP_PUBLIC_KEY = PropertiesUtil.getProperty("ap.publickey");
}