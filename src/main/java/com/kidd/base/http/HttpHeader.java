package com.kidd.base.http;


import java.io.Serializable;

import com.kidd.base.serialize.KiddSerialTypeEnum;
import com.kidd.base.utils.ToStringUtils;

public class HttpHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 协议版本号 **/
	private String version;
	/** 会话ID **/
	private String sessionID;
	/** 数据校验值 **/
	private String signature;

	/** 数据内容长度 **/
	private String dataLength;
	/** 协议内容类型 **/
	private String contentType;
	/** 密钥协商请求数据 **/
	private String keyExchange;

	/** 客户端数据类型 **/
	private KiddSerialTypeEnum dataType;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDataLength() {
		return dataLength;
	}

	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getKeyExchange() {
		return keyExchange;
	}

	public void setKeyExchange(String keyExchange) {
		this.keyExchange = keyExchange;
	}

	public KiddSerialTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(KiddSerialTypeEnum dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("version", version).add("sessionID", sessionID)
				.add("signature", signature).add("dataLength", dataLength)
				.add("contentType", contentType)
				.add("keyExchange", keyExchange).add("dataType", dataType);
		return builder.toString();
	}
	
}
