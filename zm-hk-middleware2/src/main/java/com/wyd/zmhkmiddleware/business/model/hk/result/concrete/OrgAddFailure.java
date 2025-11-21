
package com.wyd.zmhkmiddleware.business.model.hk.result.concrete;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class OrgAddFailure {

    @Expose
    private Long clientId;
    @Expose
    private String code;
    @Expose
    private String msg;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
