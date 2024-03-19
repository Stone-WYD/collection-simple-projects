package com.njxnet.asset_manage.config;


import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.common.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    

    @ExceptionHandler(RuntimeException.class)
    public AjaxResult<?> onRuntimeException(RuntimeException runtimeException) {
        BaseException be;
        // BaseException 在被抛出后会封装到 RuntimeException 中
        Throwable cause = runtimeException.getCause();
        if (cause instanceof BaseException) {
            be = (BaseException) cause;
        } else throw runtimeException;

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
