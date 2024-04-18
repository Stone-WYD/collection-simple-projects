package com.njxnet.asset_manage.config;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.common.BaseException;
import com.njxnet.asset_manage.common.ResultStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public AjaxResult handlerException(NotRoleException e) {
        e.printStackTrace();
        return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), "缺少角色：" + e.getRole(), 500);
    }

    // 登录或者鉴权异常处理
    @ExceptionHandler(NotLoginException.class)
    public AjaxResult onNotLoginException(NotLoginException nle){
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
        return new AjaxResult(null, ResultStatusCode.FAIL.getCode(), message);
    }
    

    @ExceptionHandler(BaseException.class)
    public AjaxResult<?> onRuntimeException(BaseException be) {
        AjaxResult<?> ajaxResult = new AjaxResult<>();
        int errorCode = be.getCode();
        ajaxResult.setCode(errorCode);
        ajaxResult.setMessage(be.getMessage());
        return ajaxResult;
    }



    // 加入一个通用的全局异常处理
    @ExceptionHandler(Exception.class)
    public AjaxResult<?> onException(Exception e){
        // 将异常打印出来
        log.error(e.getMessage());
        // 将异常封装成result返回给前端
        return AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>());
    }


}
