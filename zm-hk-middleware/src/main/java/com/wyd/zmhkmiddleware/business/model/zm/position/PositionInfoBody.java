
package com.wyd.zmhkmiddleware.business.model.zm.position;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PositionInfoBody {

    @SerializedName("IT_POST_BASIC")
    private List<ITPOSTBASIC> mITPOSTBASIC;

    public List<ITPOSTBASIC> getITPOSTBASIC() {
        return mITPOSTBASIC;
    }

    public void setITPOSTBASIC(List<ITPOSTBASIC> iTPOSTBASIC) {
        mITPOSTBASIC = iTPOSTBASIC;
    }

}
