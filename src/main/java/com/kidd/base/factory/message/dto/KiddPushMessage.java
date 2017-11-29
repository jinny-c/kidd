package com.kidd.base.factory.message.dto;

import com.kidd.base.common.constant.KiddConstants;

/**
 * 业务推送消息
 *
 * @history
 */
public class KiddPushMessage implements IPubNumMessage {
	private String msgKey;
	private String openId;
	private String pubId;
	private String jsonData;

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	public String getWechatMsgType() {
		return KiddConstants.MSG_TYPE_TEXT;
	}

	@Override
	public String getWechatMsgKey() {
		return getMsgKey();
	}

	@Override
	public String getWechatMsgBody() {
		return getJsonData();
	}

	@Override
	public String getWechatOpenId() {
		return getOpenId();
	}

	@Override
	public String getWechatPubId() {
		return getPubId();
	}
}