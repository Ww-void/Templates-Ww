package com.wml.config;

import com.wml.interceptor.JwtTokenAdminInterceptor; // 自定义的 JWT 拦截器类

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j; // 日志注解，提供日志记录功能

/**
 * 配置类，注册web层相关组件
 */
@Configuration // 标记此类为 Spring 的配置类
@Slf4j // 使用 Lombok 自动生成日志对象 log
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor; // 注入自定义的 JWT 拦截器

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");

        // 将 jwtTokenAdminInterceptor 拦截器注册到 Spring MVC 中
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**") // 拦截以 /admin/** 开头的请求路径
                .excludePathPatterns(""); // 可在此处配置排除某些路径不被拦截
    }


    /**
     * 通过 Knife4j 生成接口文档
     * @return OpenAPI 对象，用于 Knife4j 的配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("big-event") // 文档标题
                        .version("1.0") // 文档版本
                        .description("人力资源管理系统项目") // 文档介绍
                        .contact(new Contact()
                                .name("wml-w") // 作者姓名
                                .url("https://github.com/Ww-void") // 作者网址
                                .email("1686275715@qq.com"))); // 作者邮箱
    }

    /**
     * 设置静态资源映射，供前端访问 Swagger 和 Knife4j 的页面
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 Swagger UI 的静态页面 doc.html 映射到指定资源路径
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // 映射 webjars 资源，供 Swagger 依赖使用
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
