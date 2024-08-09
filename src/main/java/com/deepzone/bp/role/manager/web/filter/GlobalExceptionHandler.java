package com.deepzone.bp.role.manager.web.filter;

import com.deepzone.bp.role.manager.exception.BizException;
import com.deepzone.bp.role.manager.exception.SystemException;
import com.deepzone.bp.role.manager.web.model.ResponseModel;
import com.deepzone.bp.role.manager.web.util.ResponseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Rest Controller全局异常处理
 *
 * @author yangfeng
 * created 2024/8/8 15:49 v1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BizException.class})
    public ResponseModel handleBizException(BizException ex) {
        log.warn(String.format("errorCode:%s errorMessage:%s", ex.getErrCode(), ex.getErrMessage()), ex);
        return ResponseResultUtil.fail(ex.getErrCode(), ex.getErrMessage());
    }

    @ExceptionHandler(value = {SystemException.class})
    public ResponseModel handleSystemException(SystemException ex) {
        log.error(String.format("errorCode:%s errorMessage:%s", ex.getErrCode(), ex.getErrMessage()), ex);
        return ResponseResultUtil.fail(ex.getErrCode(), ex.getErrMessage());
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseModel handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseResultUtil.fail("HttpMessageNotReadable", "Fail to read http request parameter");
    }

    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public ResponseModel handleUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return ResponseResultUtil.fail("MaxUploadSizeExceeded", "File for upload is too large");
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseModel handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseResultUtil.fail("HttpMethodNotSupport", "The http method is not support for this service");
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseModel handleThrowable(Throwable ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResultUtil.fail("InternalError", "Internal error");
    }
}
