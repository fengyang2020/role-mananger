package com.deepzone.bp.role.manager.web.filter;

import com.deepzone.bp.role.manager.web.context.UserInfoContext;
import com.deepzone.bp.role.manager.model.UserInfo;
import com.deepzone.bp.role.manager.web.model.ResponseModel;
import com.deepzone.bp.role.manager.util.JsonUtil;
import com.deepzone.bp.role.manager.web.util.ResponseResultUtil;
import com.deepzone.bp.role.manager.web.util.RoleInfoParserUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;



import java.io.IOException;
import java.util.Objects;

/**
 * header身份过滤器
 *
 * @author yangfeng
 * created 2024/8/8 14:51 v1.0
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/*", filterName = "roleInfoHeaderFilter")
public class RoleInfoHeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>> roleInfoHeaderFilter init success");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // step 1: 获取header
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String roleHeader = httpRequest.getHeader("role_info");

        // step 2: 解析头信息
        UserInfo userInfo = RoleInfoParserUtil.parseRoleInfo(roleHeader);

        // step 3: 判断登陆信息是否存在
        if (Objects.isNull(userInfo)) {
            // header中无角色信息或信息非法
            sendNoLoginResponse(servletResponse);
            return;
        }

        // step 4: 写入当前请求所在线程上下文
        try {
            UserInfoContext.set(userInfo);

            // 请求放行
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 清理上下文
            UserInfoContext.clear();
        }
    }

    private void sendNoLoginResponse(ServletResponse servletResponse) throws IOException {
        ResponseModel responseModel = ResponseResultUtil.fail("UserNeedLogin", "You have not login");
        String jsonResponse = JsonUtil.toJSONString(responseModel);
        servletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        servletResponse.getWriter().write(jsonResponse);
    }

    @Override
    public void destroy() {

    }
}
