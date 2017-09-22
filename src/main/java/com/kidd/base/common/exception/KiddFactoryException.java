package com.kidd.base.common.exception;


public class KiddFactoryException extends KiddException {
	private static final long serialVersionUID = 1L;

	public KiddFactoryException() {
	}

	public KiddFactoryException(Throwable caused) {
		super(caused);
	}

	public KiddFactoryException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

	public KiddFactoryException(String errorCode, Throwable caused) {
		super(errorCode, caused);
	}

	public KiddFactoryException(String errorCode, String errorMsg,
			Throwable caused) {
		super(errorCode, errorMsg, caused);
	}
}
