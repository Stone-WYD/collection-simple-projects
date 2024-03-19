package com.wyd.satokendemospringboot.demos.common;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.wyd.satokendemospringboot.demos.common.ex.BaseException;
import com.wyd.satokendemospringboot.demos.common.ex.DBException;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultStatusCode;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;

import lombok.extern.slf4j.Slf4j;


import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public MyResult handlerException(NotPermissionException e) {
        e.printStackTrace();
        return MyResultUtil.getBussinessErrorResult("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public MyResult handlerException(NotRoleException e) {
        e.printStackTrace();
        return MyResultUtil.getBussinessErrorResult("缺少角色：" + e.getRole());
    }

    @ExceptionHandler(DBException.class)
    public MyResult onDBException(DBException dbe){
        // 异常信息输出
        log.error(dbe.getMessage());

        // 返回前端信息
        return MyResultUtil.getFalseResult(new MyResult(),
                MyResultStatusCode.DATABASE_ERROR.getName(),
                MyResultStatusCode.DATABASE_ERROR.getCode());
    }

    // 登录或者鉴权异常处理
    @ExceptionHandler(NotLoginException.class)
    public MyResult onNotLoginException(NotLoginException nle){
        // 异常信息输出
        nle.printStackTrace();

        // 判断场景值，定制化异常信息
        String message;
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        }
        else {
            message = "当前会话未登录";
        }
        return new MyResult(null, Integer.parseInt(nle.getType()), message);
    }

    // 校验异常处理
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public MyResult onValidateException(Exception ve){
        // 异常信息输出到日志
        ve.printStackTrace();

        MyResult myResult = new MyResult();
        // 获取校验结果
        BindingResult bindingResult = null;
        if (ve instanceof MethodArgumentNotValidException)
            bindingResult = ((MethodArgumentNotValidException) ve).getBindingResult();
        else if (ve instanceof BindException) bindingResult = ((BindException) ve).getBindingResult();
        // 获取校验错误的信息
        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult != null) {
            bindingResult.getFieldErrors().forEach(fe->errorMap.put(fe.getField(), fe.getDefaultMessage()));
        }
        // 将错误信息封装成Result后返回
        myResult.setCode(MyResultStatusCode.REQUEST_AUTH_FAILED.getCode());
        myResult.setMessage(MyResultStatusCode.REQUEST_AUTH_FAILED.getName());
        myResult.setData(errorMap);
        return myResult;
    }

    // 处理一些自定义异常
    @ExceptionHandler(BaseException.class)
    public MyResult onMyException(BaseException be){
        // 打印异常
        be.printStackTrace();

        // 返回结果
        MyResult myResult = new MyResult();
        myResult.setCode(MyResultStatusCode.BUSSINESS_ERROR.getCode());
        myResult.setMessage(be.getMessage());
        return myResult;
    }

    // 加入一个通用的全局异常处理
    @ExceptionHandler(Exception.class)
    public MyResult onException(Exception e){
        // 将异常打印出来
        e.printStackTrace();

        // 将异常封装成result返回给前端
        MyResult myResult = new MyResult();
        return MyResultUtil.getDefaultFalseResult(myResult);
    }
}
