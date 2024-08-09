package com.deepzone.bp.role.manager.web.filter.aop;

import com.deepzone.bp.role.manager.enums.RoleTypeEnum;
import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

/**
 * 接口角色控制注解
 *
 * @author yangfeng
 * created 2024/8/8 16:01 v1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@Inherited
public @interface ApiRole {

    /**
     * 允许访问的角色列表,不做配置则不做限制
     *
     * @return 角色列表
     */
    RoleTypeEnum[] allowRoles() default {};
}
