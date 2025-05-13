
package com.wyd.zmhkmiddleware.business.model.hk.query;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class HaiKangOrg {

    @Expose
    private Long clientId;
    @Expose
    private String orgIndexCode;
    @Expose
    private String orgName;
    @Expose
    private String parentIndexCode;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getParentIndexCode() {
        return parentIndexCode;
    }

    public void setParentIndexCode(String parentIndexCode) {
        this.parentIndexCode = parentIndexCode;
    }

}
