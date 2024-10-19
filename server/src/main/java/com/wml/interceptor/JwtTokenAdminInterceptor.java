package com.wml.interceptor;

import com.wml.constant.JwtClaimsConstant; // 常量类，包含 JWT 中的 claim 键值
import com.wml.properties.JwtProperties; // 配置类，包含 JWT 的配置属性
import com.wml.utils.JwtUtil; // 工具类，用于解析和生成 JWT

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims; // JJWT 库中的 Claims 类，表示 JWT 中的声明（claims）
import lombok.extern.slf4j.Slf4j; // 日志注解，提供简便的日志记录方式

/**
 * JWT 令牌校验的拦截器
 */
@Component // 将该拦截器类标记为 Spring 管理的 Bean，便于在项目中使用
@Slf4j // 使用 Lombok 提供的日志功能，自动生成 log 对象
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties; // 自动注入 JwtProperties 对象，获取 JWT 配置

    /**
     * 校验 JWT 令牌
     *
     * @param request HTTP 请求对象
     * @param response HTTP 响应对象
     * @param handler 当前请求的处理器对象
     * @return boolean 返回 true 表示放行，false 表示拦截
     * @throws Exception 异常抛出
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的请求是否为 Controller 的方法
        if (!(handler instanceof HandlerMethod)) {
            // 如果拦截到的不是一个控制器方法（如静态资源等），直接放行
            return true;
        }

        // 1. 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName()); // 通过 JwtProperties 获取请求头中的 token 名称

        // 2. 校验令牌
        try {
            log.info("JWT 校验:{}", token); // 记录日志，输出校验的令牌
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token); // 解析 JWT 令牌，使用密钥进行校验

            // 从 JWT 中获取员工 ID
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString()); // 获取并转换员工 ID
            log.info("当前员工ID：{}", empId); // 记录日志，输出当前员工 ID

            // 3. 令牌校验通过，放行请求
            return true;
        } catch (Exception ex) {
            // 4. 如果校验不通过，响应 401 状态码
            log.error("JWT 校验失败: {}", ex.getMessage()); // 记录校验失败的日志
            response.setStatus(401); // 设置 HTTP 响应状态码为 401（未经授权）
            return false; // 拦截请求
        }
    }
}
