package com.kidd.base.spring.modelview;

import java.io.Serializable;

public class RespSucc<E> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String responseCode = "0000";
	private String errMessage;
	private E result;

	public RespSucc() {
		super();
	}
	
	public RespSucc(E result) {
		super();
		this.result = result;
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

	public E getResult() {
		return result;
	}

	public void setResult(E result) {
		this.result = result;
	}
	
}