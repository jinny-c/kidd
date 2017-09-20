package com.kidd.base.spring.modelview;

import java.io.Serializable;

public class RespSucc implements Serializable {
	private static final long serialVersionUID = 1L;
	private String responseCode = "0000";

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
}