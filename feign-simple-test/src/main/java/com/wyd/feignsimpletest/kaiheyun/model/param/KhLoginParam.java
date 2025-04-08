package com.wyd.feignsimpletest.kaiheyun.model.param;

import com.google.gson.annotations.SerializedName;

public class KhLoginParam {

    @SerializedName("Code")
    private String code;
    @SerializedName("Password")
    private String password;
    @SerializedName("Username")
    private String username;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
