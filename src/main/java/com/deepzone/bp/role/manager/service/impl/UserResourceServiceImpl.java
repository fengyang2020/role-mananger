package com.deepzone.bp.role.manager.service.impl;

import com.deepzone.bp.role.manager.exception.BizException;
import com.deepzone.bp.role.manager.model.UserInfo;
import com.deepzone.bp.role.manager.service.UserResourceService;
import com.deepzone.bp.role.manager.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 用户资源管理服务实现
 *
 * @author yangfeng
 * created 2024/8/8 17:24 v1.0
 */
@Service
public class UserResourceServiceImpl implements UserResourceService, InitializingBean {

    @Value("${user-info.dir:}")
    private String userInfoDir;

    /**
     * 存储用户信息的文件
     */
    private File userInfoFile;

    /**
     * 用户信息
     */
    private final Map<String, UserInfo> userInfoMap = new HashMap<>();

    @Override
    public boolean addResource(String userId, List<String> addResources) {
        if (CollectionUtils.isEmpty(addResources)) {
            return true;
        }

        synchronized (this) {
            // 查询用户信息
            UserInfo userInfo = userInfoMap.get(userId);
            if (Objects.isNull(userInfo)) {
                throw new BizException("NoSuchUser", "User info not found");
            }

            // 用户资源追加
            addResourcesForUser(userInfo, addResources);

            // 重新写入文件
            saveUserInfoToFile();
        }
        return true;
    }

    private void saveUserInfoToFile() {
        try {
            Collection<UserInfo> userInfos = userInfoMap.values();
            String jsonContent = JsonUtil.toJSONString(userInfos);
            FileUtils.write(userInfoFile, jsonContent, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new BizException("SaveUserInfoFail", "Save user info failed", ex);
        }
    }

    private void addResourcesForUser(UserInfo userInfo, List<String> addResources) {
        List<String> resources = userInfo.getResources();
        if (Objects.isNull(resources)) {
            resources = new ArrayList<>();
        }

        // 集合合并
        List<String> newResources = ListUtils.union(resources, addResources);
        // 资源去重
        newResources = newResources.stream().distinct().toList();

        // 更新用户资源清单
        userInfo.setResources(newResources);
    }


    @Override
    public boolean checkResource(String userId, String resource) {
        UserInfo userInfo = userInfoMap.get(userId);

        // 无此用户信息
        if (Objects.isNull(userInfo)) {
            throw new BizException("NoSuchUser", "User info not found");
        }

        List<String> resourceList = userInfo.getResources();

        // 用户无可访问资源
        if (CollectionUtils.isEmpty(resourceList)) {
            return false;
        }

        // 用户资源是否有查询资源
        return resourceList.contains(resource);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // step 1: 校验文件信息目录
        if (StringUtils.isBlank(userInfoDir)) {
            throw new RuntimeException("Config ${role-info.dir} is blank");
        }
        File dir = new File(userInfoDir);
        if (!dir.exists()) {
            throw new RuntimeException(String.format("Config ${role-info.dir} directory:%s dose not exist", userInfoDir));
        }
        if (dir.isFile()) {
            throw new RuntimeException(String.format("Config ${role-info.dir} value:%s is not a directory", userInfoDir));
        }

        // step 2: 获取文件信息(没有则创建)
        userInfoFile = new File(dir, "user-info-data.dat");
        // 不存在则创建
        if (!userInfoFile.exists()) {
            userInfoFile.createNewFile();
        }

        // step 3: 读取文件，若没有用户信息，则直接初始化一些
        readAndInit();
    }

    private void readAndInit() throws IOException {
        String content = FileUtils.readFileToString(userInfoFile, StandardCharsets.UTF_8);
        if (StringUtils.isNotBlank(content)) {
            List<UserInfo> userInfos = JsonUtil.parseList(content, UserInfo.class);
            // 加载到内存中
            transferToMap(userInfos);
        } else {
            // 从src/main/resources/usersInfos.json中初始化用户
            InputStream inputStream = UserResourceServiceImpl.class.getClassLoader().getResourceAsStream("userInfos.json");

            String initContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            List<UserInfo> userInfos = JsonUtil.parseList(initContent, UserInfo.class);

            // 追加到内存表中
            transferToMap(userInfos);

            // 保存到文件中
            saveUserInfoToFile();
        }
    }

    private void transferToMap(List<UserInfo> userInfos) {
        if (CollectionUtils.isEmpty(userInfos)) {
            return;
        }
        for (UserInfo userInfo : userInfos) {
            userInfoMap.put(userInfo.getUserId(), userInfo);
        }
    }
}