package com.kidd.test.thread.sync;

import lombok.Getter;

public class AsyncTaskException extends Exception {
    @Getter
    private String errorCode;

    @Getter
    private String errorMsg;

    public AsyncTaskException() {
    }

    public AsyncTaskException(String errorCode, String errorMsg) {
        super(errorCode + "#" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public AsyncTaskException(String errorCode, Throwable caused) {
        super(errorCode, caused);
        this.errorCode = errorCode;
    }

    public AsyncTaskException(String errorCode, String errorMsg, Throwable caused) {
        super(errorCode + "#" + errorMsg, caused);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}