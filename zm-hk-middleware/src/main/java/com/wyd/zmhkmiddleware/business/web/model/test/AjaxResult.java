package com.wyd.zmhkmiddleware.business.web.model.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xh
 * @date 2024-12-16
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AjaxResult<T> {

    private T data;

    private int code;

    private String message;

    public AjaxResult(int code, String name) {
        AjaxResult<?> result = new AjaxResult<>();
        result.setMessage(name);
        result.setCode(code);
    }

    public static <T> AjaxResult<T> getTrueAjaxResult(T data) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(200);
        result.setMessage("成功！");
        if (data != null) {
            result.setData(data);
        }
        return result;
    }
}
