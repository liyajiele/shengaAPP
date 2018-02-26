package com.sxp.sa.basic.exception;


import com.sxp.sa.basic.utils.Rst;

/**
 * Created by miss on 2015/10/20.
 * 异常处理基础类
 */
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected int errorCode;

    protected Object dataResult;

    protected BaseException() {
        super();
    }

    protected BaseException(String errorMsg) {
        super(errorMsg);
    }

    protected BaseException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    protected BaseException(int errorCode, String errorMsg, Rst dataResult) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    protected BaseException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    protected BaseException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    protected BaseException(int errorCode, String errorMsg, Rst dataResult, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getDataResult() {
        return dataResult;
    }

    public void setDataResult(Rst dataResult) {
        this.dataResult = dataResult;
    }
}
