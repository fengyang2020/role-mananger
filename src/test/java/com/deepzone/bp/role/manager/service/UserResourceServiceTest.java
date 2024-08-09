package com.deepzone.bp.role.manager.service;

import com.deepzone.bp.role.manager.exception.BizException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangfeng
 * created 2024/8/9 09:06 v1.0
 */
@SpringBootTest
public class UserResourceServiceTest {

    @Autowired
    private UserResourceService userResourceService;

    /**
     * 增加可访问资源
     */
    @Test
    public void addResourceSuccess() {
        String userId = "123456";
        List<String> addResources = new ArrayList<>();
        addResources.add("add-comment");

        // 添加成功
        boolean addSuccess = userResourceService.addResource(userId, addResources);
        Assertions.assertTrue(addSuccess);
    }

    /**
     * 增加可访问资源
     */
    @Test
    public void addResourceFail() {
        String userId = "123456-no-such-user";
        List<String> addResources = new ArrayList<>();
        addResources.add("add-comment");
        try {
            userResourceService.addResource(userId, addResources);
        } catch (Exception ex) {
            Assertions.assertTrue(ex instanceof BizException);
            BizException bizEx = (BizException) ex;
            Assertions.assertEquals(bizEx.getErrCode(), "NoSuchUser");
        }
    }

    /**
     * 判断用户是否有此资源
     */
    @Test
    public void checkResource() {
        // 先写入数据
        String userId = "123456";
        List<String> addResources = new ArrayList<>();
        addResources.add("add-comment");
        userResourceService.addResource(userId, addResources);

        // 验证有权限情况
        boolean hasResource = userResourceService.checkResource(userId, "add-comment");
        Assertions.assertTrue(hasResource);

        // 无此权限
        boolean hasNoResource = userResourceService.checkResource(userId, "add-comment-edit");
        Assertions.assertFalse(hasNoResource);

        // 无此用户
        try {
            userResourceService.checkResource(userId + "-no-sucheuser", "add-comment");
        } catch (Exception ex) {
            Assertions.assertTrue(ex instanceof BizException);
            BizException bizEx = (BizException) ex;
            Assertions.assertEquals(bizEx.getErrCode(), "NoSuchUser");
        }
    }
}
