package com.rds.mituan.common;

import com.rds.mituan.controller.EmployeeController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> sqlhandler(SQLIntegrityConstraintViolationException ex){
        if(ex.getMessage().contains("Duplicate")){
            return R.error("用户名已存在");
        }
        return R.error("未知错误");
    }
}
