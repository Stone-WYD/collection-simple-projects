package com.wyd.zmhkmiddleware.config;


import com.wyd.zmhkmiddleware.common.AjaxResult;
import com.wyd.zmhkmiddleware.common.AjaxResultUtil;
import com.wyd.zmhkmiddleware.common.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    

    @ExceptionHandler(BaseException.class)
    public AjaxResult<?> onRuntimeException(BaseException be) {
        AjaxResult<?> ajaxResult = new AjaxResult<>();
        int errorCode = be.getCode();
        ajaxResult.setCode(errorCode);
        ajaxResult.setMsg(be.getMessage());
        return ajaxResult;
    }

    // 处理@Valid校验失败的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return AjaxResultUtil.getFalseAjaxResult(errors);
    }


    // 加入一个通用的全局异常处理
    @ExceptionHandler(Exception.class)
    public AjaxResult<?> onException(Exception e){
        // 将异常打印出来
        log.error(e.getMessage());
        // 将异常封装成result返回给前端
        return AjaxResultUtil.getFalseAjaxResult(e.getMessage());
    }


}
