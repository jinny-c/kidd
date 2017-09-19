package com.kidd.base.modelview;

import java.io.Serializable;

public class RespErr implements Serializable {
	private static final long serialVersionUID = 1L;
	private String responseCode;
	private String errMessage;

	/**
	 * @param responseCode
	 * @param errMessage
	 */
	public RespErr(String responseCode, String errMessage) {
		//super();
		this.responseCode = responseCode;
		this.errMessage = errMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}