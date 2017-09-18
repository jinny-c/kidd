package com.kidd.base.modelview;

import java.io.Serializable;

public class RespSucc implements Serializable {
	private static final long serialVersionUID = 1L;
	private int responseCode = 0;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}