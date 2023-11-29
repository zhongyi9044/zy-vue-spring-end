package com.zy.exception;

import com.zy.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理，确保返回给前端格式相同
@RestControllerAdvice
public class GlobalExceptionHandler {

//    接收错误信息
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作错误");
    }
}
