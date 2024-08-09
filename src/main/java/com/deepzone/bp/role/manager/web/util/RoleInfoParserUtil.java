package com.deepzone.bp.role.manager.web.util;

import com.deepzone.bp.role.manager.model.UserInfo;
import com.deepzone.bp.role.manager.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

/**
 * 角色信息解析工具类
 *
 * @author yangfeng
 * created 2024/8/8 15:00 v1.0
 */
public class RoleInfoParserUtil {

    /**
     * 解析header信息中的角色信息
     *
     * @param headerInfo 被base64编码后的用户角色信息
     * @return 角色信息明文
     */
    public static UserInfo parseRoleInfo(String headerInfo) {
        if (StringUtils.isBlank(headerInfo)) {
            return null;
        }
        try {
            byte[] rowData = Base64.getDecoder().decode(headerInfo);
            String rawData = new String(rowData);
            return JsonUtil.parseObject(rawData, UserInfo.class);
        } catch (Throwable ex) {
            // TODO log.warn("")
            return null;
        }
    }
}
