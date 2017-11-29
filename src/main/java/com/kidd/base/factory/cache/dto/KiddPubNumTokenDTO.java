package com.kidd.base.factory.cache.dto;

import java.io.Serializable;

import com.kidd.base.common.utils.ToStringUtils;

/**
 * 微信公众号接口凭证
 *
 * @history
 */
public class KiddPubNumTokenDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 微信公众号 【原始ID（微信号）：gh_xxxx】 */
	private String pubNo;

	/** 开发者ID */
	private String appId;

	/** 开发者Secret */
	private String appSecret;

	private String accessTokenUrl;

	private String jsApiTicketUrl;

	private String accessToken;

	private String jsApiTicket;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 会话失效时间
	 */
	private String invalidTime;

	/**
	 * 是否强制刷新
	 */
	private boolean mandatory;

	public String getPubNo() {
		return pubNo;
	}

	public void setPubNo(String pubNo) {
		this.pubNo = pubNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	public String getJsApiTicketUrl() {
		return jsApiTicketUrl;
	}

	public void setJsApiTicketUrl(String jsApiTicketUrl) {
		this.jsApiTicketUrl = jsApiTicketUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getJsApiTicket() {
		return jsApiTicket;
	}

	public void setJsApiTicket(String jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("pubNo", pubNo).add("appId", appId)
				.add("appSecret", appSecret)
				.add("accessTokenUrl", accessTokenUrl)
				.add("jsApiTicketUrl", jsApiTicketUrl)
				.add("accessToken", accessToken)
				.add("jsApiTicket", jsApiTicket).add("createTime", createTime)
				.add("invalidTime", invalidTime).add("mandatory", mandatory);
		return builder.toString();
	}
}
