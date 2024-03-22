package com.os.exception;

import com.os.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: lj
 * @desc
 * @create: 2024.01.20
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.fail("操作失败", StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");
    }
}
