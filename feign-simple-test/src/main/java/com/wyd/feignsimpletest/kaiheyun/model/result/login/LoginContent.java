
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginContent {

    @SerializedName("Card")
    private Object card;
    @SerializedName("Gender")
    private Object gender;
    @SerializedName("HeadImageUrl")
    private Object headImageUrl;
    @SerializedName("Id")
    private Long id;
    @SerializedName("IsChief")
    private Boolean isChief;
    @SerializedName("IsWechatBind")
    private Boolean isWechatBind;
    @SerializedName("Name")
    private String name;
    @SerializedName("Station")
    private Station station;
    @SerializedName("StationRelations")
    private List<Object> stationRelations;
    @SerializedName("Telephone")
    private Object telephone;
    @SerializedName("Token")
    private String token;
    @SerializedName("UserType")
    private Long userType;
    @SerializedName("Username")
    private String username;
    @SerializedName("WechatNickname")
    private Object wechatNickname;

    public Object getCard() {
        return card;
    }

    public void setCard(Object card) {
        this.card = card;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(Object headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsChief() {
        return isChief;
    }

    public void setIsChief(Boolean isChief) {
        this.isChief = isChief;
    }

    public Boolean getIsWechatBind() {
        return isWechatBind;
    }

    public void setIsWechatBind(Boolean isWechatBind) {
        this.isWechatBind = isWechatBind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Object> getStationRelations() {
        return stationRelations;
    }

    public void setStationRelations(List<Object> stationRelations) {
        this.stationRelations = stationRelations;
    }

    public Object getTelephone() {
        return telephone;
    }

    public void setTelephone(Object telephone) {
        this.telephone = telephone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getWechatNickname() {
        return wechatNickname;
    }

    public void setWechatNickname(Object wechatNickname) {
        this.wechatNickname = wechatNickname;
    }

}
