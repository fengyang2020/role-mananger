package com.deepzone.bp.role.manager.web.filter.aop;

import com.deepzone.bp.role.manager.enums.RoleTypeEnum;
import com.deepzone.bp.role.manager.model.UserInfo;
import com.deepzone.bp.role.manager.web.context.UserInfoContext;
import com.deepzone.bp.role.manager.web.util.ResponseResultUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 接口角色拦截器
 *
 * @author yangfeng
 * created 2024/8/8 16:04 v1.0
 */
@Component
@Aspect
public class ApiRoleFilterAspect {

    /**
     * Controller类级别注解
     */
    @Pointcut(value = "@within(com.deepzone.bp.role.manager.web.filter.aop.ApiRole)")
    public void controllerPointcut() {
    }

    @Around(value = "controllerPointcut()")
    public Object controllerRound(ProceedingJoinPoint joinPoint) throws Throwable {
        return doExecute(joinPoint);
    }

    /**
     * RequestMapping方法级别注解
     */
    @Pointcut(value = "@annotation(com.deepzone.bp.role.manager.web.filter.aop.ApiRole)")
    public void controllerMethodPointcut() {
    }

    @Around(value = "controllerMethodPointcut()")
    public Object controllerMethodRound(ProceedingJoinPoint joinPoint) throws Throwable {
        return doExecute(joinPoint);
    }

    private Object doExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class clazz = signature.getDeclaringType();
        ApiRole apiRole = getAnnotation(clazz, method);

        // 拿到用户上下文
        UserInfo userInfo = UserInfoContext.get();
        if (Objects.isNull(userInfo)) {
            return ResponseResultUtil.fail("UserNeedLogin", "You have not login");
        }

        // 检查用户角色是否被允许
        if (checkRoleAllowed(apiRole.allowRoles(), userInfo.getRole())) {
            return joinPoint.proceed();
        } else {
            return ResponseResultUtil.fail("RoleNotAllowed", "Current role is not allowed for this service");
        }
    }

    private boolean checkRoleAllowed(RoleTypeEnum[] roles, String loginUserRole) {
        // 无配置允许角色，放开所有
        if (ArrayUtils.isEmpty(roles)) {
            return true;
        }
        for (RoleTypeEnum roleType : roles) {
            if (StringUtils.endsWithIgnoreCase(roleType.getCode(), loginUserRole)) {
                return true;
            }
        }
        return false;
    }

    private ApiRole getAnnotation(Class clazz, Method method) {
        // 优先读方法级别的注解
        ApiRole methodAnnotation = method.getAnnotation(ApiRole.class);
        if (Objects.nonNull(methodAnnotation)) {
            return methodAnnotation;
        }
        // 读取类级别的注解
        return (ApiRole) clazz.getAnnotation(ApiRole.class);
    }
}
