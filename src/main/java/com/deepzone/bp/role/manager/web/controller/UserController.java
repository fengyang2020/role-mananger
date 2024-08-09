package com.deepzone.bp.role.manager.web.controller;

import com.deepzone.bp.role.manager.enums.RoleTypeEnum;
import com.deepzone.bp.role.manager.model.UserInfo;
import com.deepzone.bp.role.manager.service.UserResourceService;
import com.deepzone.bp.role.manager.web.context.UserInfoContext;
import com.deepzone.bp.role.manager.web.filter.aop.ApiRole;
import com.deepzone.bp.role.manager.web.model.ResponseModel;
import com.deepzone.bp.role.manager.web.util.ResponseResultUtil;
import com.deepzone.bp.role.manager.web.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息Controller
 *
 * @author yangfeng
 * created 2024/8/8 15:37 v1.0
 */
@RestController
@RequestMapping("/user")
@ApiRole(allowRoles = {RoleTypeEnum.USER})
public class UserController {

    @Autowired
    private UserResourceService userResourceService;

    /**
     * 查看当前用户是否有查询资源的访问权限
     *
     * @param resource 检查资源项
     * @return
     */
    @PostMapping(path = "/{resource}")
    public ResponseModel<Boolean> checkResource(@PathVariable("resource") String resource) {
        // 参数校验
        UserValidator.validate(resource);

        UserInfo userInfo = UserInfoContext.get();

        // 检查是否有此资源
        Boolean hasResource = userResourceService.checkResource(userInfo.getUserId(), resource);

        return ResponseResultUtil.success(hasResource);
    }
}
