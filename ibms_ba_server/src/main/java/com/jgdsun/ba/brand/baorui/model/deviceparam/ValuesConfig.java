
package com.jgdsun.ba.brand.baorui.model.deviceparam;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ValuesConfig {

    @Expose
    @SerializedName("false")
    private String fail;
    @Expose
    @SerializedName("true")
    private String success;

    public String getFalse() {
        return fail;
    }

    public void setFalse(String fail) {
        this.fail = fail;
    }

    public String getTrue() {
        return success;
    }

    public void setTrue(String success) {
        this.success = success;
    }

}
