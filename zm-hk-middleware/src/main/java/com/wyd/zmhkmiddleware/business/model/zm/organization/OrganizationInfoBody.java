
package com.wyd.zmhkmiddleware.business.model.zm.organization;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrganizationInfoBody {

    @SerializedName("IT_ORG_BASIC")
    private List<ITORGBASIC> iTORGBASIC;

    public List<ITORGBASIC> getITORGBASIC() {
        return iTORGBASIC;
    }

    public void setITORGBASIC(List<ITORGBASIC> iTORGBASIC) {
        this.iTORGBASIC = iTORGBASIC;
    }

}
