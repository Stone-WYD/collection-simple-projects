package com.wyd.asset_manage.common;

public class AjaxResultUtil {

    public static <T> AjaxResult<T> getTrueAjaxResult() {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setCode(ResultStatusCode.SUCCESS.getCode());
        ajaxResult.setMessage(ResultStatusCode.SUCCESS.getName());
        return ajaxResult;
    }

    public static <T> AjaxResult<T> getTrueAjaxResult(T data) {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setData(data);
        ajaxResult.setCode(ResultStatusCode.SUCCESS.getCode());
        ajaxResult.setMessage(ResultStatusCode.SUCCESS.getName());
        return ajaxResult;
    }

    public static <T> AjaxResult<T> getFalseAjaxResult(T data) {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setData(data);
        ajaxResult.setCode(ResultStatusCode.ERROR.getCode());
        ajaxResult.setMessage(ResultStatusCode.ERROR.getName());
        return ajaxResult;
    }
}
