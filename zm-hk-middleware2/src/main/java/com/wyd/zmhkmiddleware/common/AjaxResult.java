package com.wyd.zmhkmiddleware.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AjaxResult<T> {

    private T data;

    private int code;

    private String msg;

    public AjaxResult(int code, String name) {
        AjaxResult<?> result = new AjaxResult<>();
        result.setMsg(name);
        result.setCode(code);
    }
}
