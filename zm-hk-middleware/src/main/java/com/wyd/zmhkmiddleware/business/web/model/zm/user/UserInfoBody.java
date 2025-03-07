
package com.wyd.zmhkmiddleware.business.web.model.zm.user;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserInfoBody {

    @SerializedName("ET_EMPL_BANK")
    private List<ETEMPLBANK> eTEMPLBANK;
    @SerializedName("ET_EMPL_BASIC")
    private List<ETEMPLBASIC> eTEMPLBASIC;
    @SerializedName("ET_EMPL_POST")
    private List<ETEMPLPOST> eTEMPLPOST;

    public List<ETEMPLBANK> getETEMPLBANK() {
        return eTEMPLBANK;
    }

    public void setETEMPLBANK(List<ETEMPLBANK> eTEMPLBANK) {
        this.eTEMPLBANK = eTEMPLBANK;
    }

    public List<ETEMPLBASIC> getETEMPLBASIC() {
        return eTEMPLBASIC;
    }

    public void setETEMPLBASIC(List<ETEMPLBASIC> eTEMPLBASIC) {
        this.eTEMPLBASIC = eTEMPLBASIC;
    }

    public List<ETEMPLPOST> getETEMPLPOST() {
        return eTEMPLPOST;
    }

    public void setETEMPLPOST(List<ETEMPLPOST> eTEMPLPOST) {
        this.eTEMPLPOST = eTEMPLPOST;
    }

}
