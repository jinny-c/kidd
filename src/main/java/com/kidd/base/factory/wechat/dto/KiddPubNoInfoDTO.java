package com.kidd.base.factory.wechat.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KiddPubNoInfoDTO implements Serializable {

	private static final long serialVersionUID = -8435893636502328478L;

	public static final String MSG_KEY_AUDIT = "audit";
	public static final String MSG_KEY_TRANS = "trans";
	public static final String MSG_KEY_WITHDRAW = "withdraw";
	public static final String MSG_KEY_CREDIT = "credit";

	/** 微信公众号 【原始ID（微信号）：gh_xxxx】 */
	private String pubId;

	/** 开发者ID */
	private String appId;

	/** 开发者Secret */
	private String appSecret;

	/** 公众号名称 */
	private String pubName;

	/** 公众号状态:00-有效、01-无效 */
	private String pubStatus;

	/** 创建时间 */
	private String createTime;

	/** 更新时间 */
	private Date uptTime;

	/** 公众号模板 **/
	private Map<String, String> tempIdMap = new HashMap<String, String>();

	/** 公众号资源URL **/
	private Map<String, String> picUrlMap = new HashMap<String, String>();

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
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

	public String getPubName() {
		return pubName;
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public String getPubStatus() {
		return pubStatus;
	}

	public void setPubStatus(String pubStatus) {
		this.pubStatus = pubStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Date getUptTime() {
		return uptTime;
	}

	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}

	public Map<String, String> getTempIdMap() {
		return tempIdMap;
	}

	public void setTempIdMap(Map<String, String> tempIdMap) {
		this.tempIdMap = tempIdMap;
	}

	public String getMsgTempId(String key) {
		return tempIdMap.get(key);
	}

	public void putMsgTempId(String key, String value) {
		this.tempIdMap.put(key, value);
	}

	public String getPicUrl(String key) {
		return picUrlMap.get(key);
	}

	public void putPicUrl(String key, String value) {
		this.picUrlMap.put(key, value);
	}
}