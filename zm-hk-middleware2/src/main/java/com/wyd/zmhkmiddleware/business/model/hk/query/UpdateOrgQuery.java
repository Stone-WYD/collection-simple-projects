
package com.wyd.zmhkmiddleware.business.model.hk.query;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class UpdateOrgQuery {

    @Expose
    private String orgIndexCode;
    @Expose
    private String orgName;

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

}
