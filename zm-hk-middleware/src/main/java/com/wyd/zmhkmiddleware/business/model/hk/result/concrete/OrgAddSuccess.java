
package com.wyd.zmhkmiddleware.business.model.hk.result.concrete;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OrgAddSuccess {

    @Expose
    private Long clientId;
    @Expose
    private String orgIndexCode;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getOrgIndexCode() {
        return orgIndexCode;
    }

    public void setOrgIndexCode(String orgIndexCode) {
        this.orgIndexCode = orgIndexCode;
    }

}
