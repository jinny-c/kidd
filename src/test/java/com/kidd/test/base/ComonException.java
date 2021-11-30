package com.kidd.test.base;

import lombok.Getter;

/**
 * @description TODO
 * @auth chaijd
 * @date 2021/11/30
 */
public class ComonException extends Exception{
    @Getter
    private String errorCode;

    @Getter
    private String errorMsg;

    public ComonException() {
    }

    public ComonException(String errorCode, String errorMsg) {
        super(errorCode + "#" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public ComonException(String errorCode, Throwable caused) {
        super(errorCode, caused);
        this.errorCode = errorCode;
    }

    public ComonException(String errorCode, String errorMsg, Throwable caused) {
        super(errorCode + "#" + errorMsg, caused);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
