
package com.wyd.zmhkmiddleware.business.model.zm;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IREQUEST<T> {

    @SerializedName("BODY")
    private T bODY;
    @SerializedName("REQ_BASEINFO")
    private REQBASEINFO rEQBASEINFO;

    public T getBODY() {
        return bODY;
    }

    public void setBODY(T bODY) {
        this.bODY = bODY;
    }

    public REQBASEINFO getREQBASEINFO() {
        return rEQBASEINFO;
    }

    public void setREQBASEINFO(REQBASEINFO rEQBASEINFO) {
        this.rEQBASEINFO = rEQBASEINFO;
    }

}
