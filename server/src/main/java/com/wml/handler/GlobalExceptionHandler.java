package com.wml.handler;

import com.wml.exception.BaseException; // 自定义异常类，表示业务逻辑中的异常
import com.wml.result.Result; // 自定义响应对象，用于标准化 API 响应格式

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j; // 日志注解，提供日志记录功能

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice // 标记为全局异常处理类，结合 @ControllerAdvice 和 @ResponseBody
@Slf4j // 使用 Lombok 提供的日志功能，自动生成 log 对象
public class GlobalExceptionHandler {

    /**
     * 捕获 BaseException 异常
     * @param ex 捕获的 BaseException 对象
     * @return 返回封装后的错误信息
     */
    @ExceptionHandler(BaseException.class) // 该注解用于捕获指定类型的异常
    public Result exceptionHandler(BaseException ex) {
        // 记录日志，打印异常的具体信息
        log.error("异常信息：{}", ex.getMessage());

        // 返回封装后的错误结果，调用 Result 类的静态方法 error
        return Result.error(ex.getMessage());
    }
}
