package com.deepzone.bp.role.manager.model;

import lombok.Data;

import java.util.List;

/**
 * 角色信息
 *
 * @author yangfeng
 * created 2024/8/8 15:00 v1.0
 */
@Data
public class UserInfo {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 用户角色code
     */
    private String role;

    /**
     * 用户授权资源
     */
    private List<String> resources;
}
