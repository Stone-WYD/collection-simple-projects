
package com.wyd.zmhkmiddleware.business.model.hk.result.concrete;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class PersonAddFailure {

    @Expose
    private Long clientId;
    @Expose
    private String code;
    @Expose
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
