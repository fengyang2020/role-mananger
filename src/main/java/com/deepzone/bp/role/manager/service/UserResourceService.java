package com.deepzone.bp.role.manager.service;

import java.util.List;

/**
 * 用户资源管理服务
 *
 * @author yangfeng
 * created 2024/8/8 15:29 v1.0
 */
public interface UserResourceService {

    /**
     * 增加可访问资源
     *
     * @param userId
     * @param addResources 增加的资源清单
     * @return
     */
    boolean addResource(String userId, List<String> addResources);

    /**
     * 判断用户是否有此资源
     *
     * @param userId
     * @param resource
     * @return
     */
    boolean checkResource(String userId, String resource);
}
