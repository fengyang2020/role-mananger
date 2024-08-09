package com.deepzone.bp.role.manager.exception;

/**
 * 业务异常
 *
 * @author yangfeng
 * created 2024/8/8 15:45 v1.0
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 631554743599177663L;

    private final String errCode;
    private final String errMessage;

    public BizException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public BizException(String errCode, String errMessage, Throwable throwable) {
        super(errMessage, throwable);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}

