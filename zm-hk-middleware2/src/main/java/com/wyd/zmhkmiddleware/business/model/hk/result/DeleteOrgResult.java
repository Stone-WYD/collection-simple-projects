
package com.wyd.zmhkmiddleware.business.model.hk.result;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DeleteOrgResult {

    @Expose
    private String code;
    @Expose
    private String msg;
    @Expose
    private String orgIndexCode;

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

    public String getOrgIndexCode() {
        return orgIndexCode;
    }

    public void setOrgIndexCode(String orgIndexCode) {
        this.orgIndexCode = orgIndexCode;
    }

}
