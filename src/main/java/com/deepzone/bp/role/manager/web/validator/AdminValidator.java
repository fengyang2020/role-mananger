package com.deepzone.bp.role.manager.web.validator;

import com.deepzone.bp.role.manager.exception.BizException;
import com.deepzone.bp.role.manager.web.form.PermissionAddForm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 管理员接口校验工具类
 *
 * @author yangfeng
 * created 2024/8/8 22:26 v1.0
 */
public class AdminValidator {

    public static void validate(PermissionAddForm addForm) {
        if (StringUtils.isBlank(addForm.getUserId())) {
            throw new BizException("ParamNotValid", "Param userId is blank");
        }

        if (CollectionUtils.isEmpty(addForm.getEndpoint())) {
            throw new BizException("ParamNotValid", "Param endpoint is empty");
        }

        for (String resource : addForm.getEndpoint()) {
            if (StringUtils.isBlank(resource)) {
                throw new BizException("ParamNotValid", "Param endpoint contains blank value");
            }
        }
    }
}
