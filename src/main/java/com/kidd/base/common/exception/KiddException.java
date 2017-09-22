package com.kidd.base.common.exception;

public class KiddException extends Exception {

	private static final long serialVersionUID = 1L;

	public KiddException() {
	}

	public KiddException(String errorCode) {
		super(errorCode);
	}

	public KiddException(Throwable caused) {
		super(caused);
	}

	public KiddException(String errorCode, String errorMsg) {
		super(errorCode + "#" + errorMsg);
	}

	public KiddException(String errorCode, Throwable caused) {
		super(errorCode, caused);
	}

	public KiddException(String errorCode, String errorMsg, Throwable caused) {
		super(errorCode + "#" + errorMsg, caused);
	}

	public String getErrorCode() {
		return resultInfo(getMessage(), Integer.valueOf(0));
	}

	public String getErrorMsg() {
		return resultInfo(getMessage(), Integer.valueOf(1));
	}

	private String resultInfo(String errorMessage, Integer index) {
		String result = "";
		if (getMessage() != null && getMessage().length() > 0) {
			String[] ary = errorMessage.split("#");
			if ((ary != null) && (ary.length > index.intValue()))
				result = ary[index.intValue()];
			else {
				result = errorMessage;
			}
		}
		return result;
	}
}
