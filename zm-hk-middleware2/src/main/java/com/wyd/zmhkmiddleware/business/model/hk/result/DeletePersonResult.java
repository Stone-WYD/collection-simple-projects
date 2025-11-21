
package com.wyd.zmhkmiddleware.business.model.hk.result;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class DeletePersonResult {

    @Expose
    private String code;
    @Expose
    private String msg;
    @Expose
    private String personId;

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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

}
