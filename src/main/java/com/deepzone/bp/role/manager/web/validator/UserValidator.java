package com.deepzone.bp.role.manager.web.validator;

import com.deepzone.bp.role.manager.exception.BizException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yangfeng
 * created 2024/8/8 22:34 v1.0
 */
public class UserValidator {

    public static void validate(String resource) {
        if (StringUtils.isBlank(resource)) {
            throw new BizException("ParamNotValid", "Param resource is blank");
        }
    }
}
