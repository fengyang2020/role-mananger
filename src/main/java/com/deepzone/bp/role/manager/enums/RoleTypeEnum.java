package com.deepzone.bp.role.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型枚举
 *
 * @author yangfeng
 * created 2024/8/8 16:01 v1.0
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    /**
     *
     */
    ADMIN("admin"),

    /**
     *
     */
    USER("user");

    /**
     *
     */
    private String code;
}
