package com.kidd.base.common.exception;


public class KiddServiceException extends KiddException {
	private static final long serialVersionUID = 1L;

	public KiddServiceException() {
	}

	public KiddServiceException(Throwable caused) {
		super(caused);
	}

	public KiddServiceException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

	public KiddServiceException(String errorCode, Throwable caused) {
		super(errorCode, caused);
	}

	public KiddServiceException(String errorCode, String errorMsg,
			Throwable caused) {
		super(errorCode, errorMsg, caused);
	}
}
