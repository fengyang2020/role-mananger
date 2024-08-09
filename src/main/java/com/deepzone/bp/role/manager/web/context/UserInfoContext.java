package com.deepzone.bp.role.manager.web.context;

import com.deepzone.bp.role.manager.model.UserInfo;

/**
 * 角色信息上下文
 *
 * @author yangfeng
 * created 2024/8/8 15:19 v1.0
 */
public class UserInfoContext {

    private static final ThreadLocal<UserInfo> context = new ThreadLocal<>();

    public static void set(UserInfo userInfo) {
        context.set(userInfo);
    }

    public static UserInfo get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
