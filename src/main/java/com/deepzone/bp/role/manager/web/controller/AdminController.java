package com.deepzone.bp.role.manager.web.controller;

import com.deepzone.bp.role.manager.enums.RoleTypeEnum;
import com.deepzone.bp.role.manager.model.UserInfo;
import com.deepzone.bp.role.manager.service.UserResourceService;
import com.deepzone.bp.role.manager.web.context.UserInfoContext;
import com.deepzone.bp.role.manager.web.filter.aop.ApiRole;
import com.deepzone.bp.role.manager.web.form.PermissionAddForm;
import com.deepzone.bp.role.manager.web.model.ResponseModel;
import com.deepzone.bp.role.manager.web.util.ResponseResultUtil;
import com.deepzone.bp.role.manager.web.validator.AdminValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理借口
 *
 * @author yangfeng
 * created 2024/8/8 14:36 v1.0
 */
@RestController
@RequestMapping("/admin")
@ApiRole(allowRoles = {RoleTypeEnum.ADMIN})
public class AdminController {

    @Autowired
    private UserResourceService userResourceService;

    /**
     * 给当前登陆用户执行授权
     *
     * @param addForm
     * @return
     */
    @PostMapping("/addUser")
    public ResponseModel<Boolean> addUser(@RequestBody PermissionAddForm addForm) {
        // 参数校验
        AdminValidator.validate(addForm);

        // 执行增加授权
        Boolean addSuccess = userResourceService.addResource(addForm.getUserId(), addForm.getEndpoint());

        return ResponseResultUtil.success(addSuccess);
    }
}
