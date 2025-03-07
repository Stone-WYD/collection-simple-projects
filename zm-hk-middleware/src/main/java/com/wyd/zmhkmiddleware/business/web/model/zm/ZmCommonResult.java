
package com.wyd.zmhkmiddleware.business.web.model.zm;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ZmCommonResult<T> implements Serializable {

    @SerializedName("I_REQUEST")
    private IREQUEST<T> iREQUEST;

    public IREQUEST getIREQUEST() {
        return iREQUEST;
    }

    public void setIREQUEST(IREQUEST<T> iREQUEST) {
        this.iREQUEST = iREQUEST;
    }

}
