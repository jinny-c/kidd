package com.kidd.base.http;


import java.io.Serializable;

import com.kidd.base.common.serialize.KiddSerialTypeEnum;
import com.kidd.base.common.utils.ToStringUtils;

public class HttpHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 客户端版本号:1.0.0,2.0.1 **/
	private String version;
	/** 机器型号(类似meizu、iphone5、ipad2)**/
	private String machineModel;
	/** 平台 ios、android**/
	private String plantform;
	/** 客户端imei码 **/
	private String imei;

	/** 会话ID **/
	private String sessionId;
	/** 数据校验值 **/
	private String signature;
	/** 通讯唯一标识 **/
	private String uniqueIdentifier;

	private String userAgent;
	/** ajax请求**/
	private String xmlRequestedWith;
	
	/** 客户端数据类型 **/
	private KiddSerialTypeEnum dataType;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public KiddSerialTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(KiddSerialTypeEnum dataType) {
		this.dataType = dataType;
	}

	public String getPlantform() {
		return plantform;
	}

	public void setPlantform(String plantform) {
		this.plantform = plantform;
	}

	public String getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getXmlRequestedWith() {
		return xmlRequestedWith;
	}

	public void setXmlRequestedWith(String xmlRequestedWith) {
		this.xmlRequestedWith = xmlRequestedWith;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("version", version).add("machineModel", machineModel)
				.add("plantform", plantform).add("imei", imei)
				.add("sessionId", sessionId).add("signature", signature)
				.add("uniqueIdentifier", uniqueIdentifier)
				.add("userAgent", userAgent)
				.add("xmlRequestedWith", xmlRequestedWith)
				.add("dataType", dataType);
		return builder.toString();
	}
	
}
