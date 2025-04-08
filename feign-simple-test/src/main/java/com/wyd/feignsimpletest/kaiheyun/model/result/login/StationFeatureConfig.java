
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StationFeatureConfig {

    @SerializedName("EnableFaceRecognize")
    private Boolean enableFaceRecognize;

    public Boolean getEnableFaceRecognize() {
        return enableFaceRecognize;
    }

    public void setEnableFaceRecognize(Boolean enableFaceRecognize) {
        this.enableFaceRecognize = enableFaceRecognize;
    }

}
