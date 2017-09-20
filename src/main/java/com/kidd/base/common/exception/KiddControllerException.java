package com.kidd.base.common.exception;

public class KiddControllerException extends KiddGlobalValidException {
	
	private static final long serialVersionUID = -7081755423334874084L;

	public KiddControllerException() {
	}

	public KiddControllerException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}
	
	public KiddControllerException(String errorCode, String errorMsg, Throwable caused) {
		super(errorCode, errorMsg, caused);
	}
}
