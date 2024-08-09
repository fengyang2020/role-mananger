package com.deepzone.bp.role.manager.web.model;

import lombok.Data;

/**
 * Rest接口返回统一模型
 *
 * @author yangfeng
 * created 2024/8/8 15:38 v1.0
 */
@Data
public class ResponseModel<T> {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 接口状态码
     */
    private String code;

    /**
     * 接口消息
     */
    private String message;

    /**
     * 业务结果
     */
    private T data;

}
