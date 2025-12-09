
package com.jgdsun.qms.model.ims4;

public class IMS4Result<T> {

    private Long code;

    private T data;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
