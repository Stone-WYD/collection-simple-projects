package com.wyd.asset_manage.config;


import com.wyd.asset_manage.common.AjaxResult;
import com.wyd.asset_manage.common.AjaxResultUtil;
import com.wyd.asset_manage.common.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    

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
