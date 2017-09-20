package com.kidd.base.common;

import java.io.Serializable;

import com.kidd.base.common.utils.ToStringUtils;

/**
 * 请求结果类
 * 
 */
public abstract class KiddBaseRespDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String responseCode = "0000";

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("responseCode", responseCode);
		return builder.toString();
	}

}
