package com.deepzone.bp.role.manager.exception;

/**
 * 系统异常(三方服务不稳定等)
 *
 * @author yangfeng
 * created 2024/8/8 17:15 v1.0
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 631554743599177663L;

    private final String errCode;
    private final String errMessage;

    public SystemException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public SystemException(String errCode, String errMessage, Throwable throwable) {
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

