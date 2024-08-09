package com.deepzone.bp.role.manager.web.util;

import com.deepzone.bp.role.manager.web.model.ResponseModel;

/**
 * @author yangfeng
 * created 2024/8/8 15:42 v1.0
 */
public class ResponseResultUtil {

    public static <T> ResponseModel<T> success(T obj) {
        ResponseModel<T> data = new ResponseModel<>();
        data.setSuccess(true);
        data.setCode("200");
        data.setMessage("Success");
        data.setData(obj);
        return data;
    }

    public static <T> ResponseModel<T> fail(String errorCode, String errorMessage) {
        ResponseModel<T> data = new ResponseModel<>();
        data.setSuccess(false);
        data.setCode(errorCode);
        data.setMessage(errorMessage);
        return data;
    }
}
