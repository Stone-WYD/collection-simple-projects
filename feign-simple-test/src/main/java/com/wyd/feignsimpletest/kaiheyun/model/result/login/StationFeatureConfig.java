
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

 

  
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
