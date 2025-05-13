package com.wyd.zmhkmiddleware.common;

import com.wyd.zmhkmiddleware.common.enums.ResultStatusCode;

public class AjaxResultUtil {
    public static <T> AjaxResult<T> getTrueAjaxResult() {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setCode(ResultStatusCode.SUCCESS.getCode());
        ajaxResult.setMsg(ResultStatusCode.SUCCESS.getName());
        return ajaxResult;
    }

    public static <T> AjaxResult<T> getTrueAjaxResult(T data) {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setData(data);
        ajaxResult.setCode(ResultStatusCode.SUCCESS.getCode());
        ajaxResult.setMsg(ResultStatusCode.SUCCESS.getName());
        return ajaxResult;
    }

    public static <T> AjaxResult<T> getFalseAjaxResult(T data) {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setData(data);
        ajaxResult.setCode(ResultStatusCode.ERROR.getCode());
        ajaxResult.setMsg(ResultStatusCode.ERROR.getName());
        return ajaxResult;
    }
}
