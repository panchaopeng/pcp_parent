package com.pcp.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @depict: 全局异常处理类
 * @author: PCP
 * @create: 2019-02-22 22:22
 */

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        return new Result(false, StatusCode.ERROR,
                e.getMessage() + e.getMessage());
    }
}
