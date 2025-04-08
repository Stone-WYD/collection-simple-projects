
package com.wyd.feignsimpletest.kaiheyun.model.result;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CommonResult<T> {

    @SerializedName("Content")
    private T content;
    @SerializedName("Message")
    private Object message;
    @SerializedName("Status")
    private Long status;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
