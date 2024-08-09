package com.deepzone.bp.role.manager.web.form;

import lombok.Data;

import java.util.List;

/**
 * 添加授权参数
 *
 * @author yangfeng
 * created 2024/8/8 15:27 v1.0
 */
@Data
public class PermissionAddForm {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 授权信息
     */
    private List<String> endpoint;
}
