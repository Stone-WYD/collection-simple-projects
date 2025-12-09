
package com.wyd.feignsimpletest.kaiheyun.model.result.device;

import com.google.gson.annotations.SerializedName;

 

  
@SuppressWarnings("unused")
public class DeviceContent {

    @SerializedName("Battery")
    private Long battery;
    @SerializedName("CardRecordCount")
    private Long cardRecordCount;
    @SerializedName("CustomerConfig")
    private Object customerConfig;
    @SerializedName("Desc")
    private String desc;
    @SerializedName("DeviceId")
    private String deviceId;
    @SerializedName("HitRecordCount")
    private Long hitRecordCount;
    @SerializedName("Id")
    private Long id;
    @SerializedName("IsBatteryLow")
    private Boolean isBatteryLow;
    @SerializedName("IsHitTooMuch")
    private Boolean isHitTooMuch;
    @SerializedName("LastHitTime")
    private Object lastHitTime;
    @SerializedName("LastUploadTime")
    private String lastUploadTime;
    @SerializedName("SIM")
    private String sIM;
    @SerializedName("Station")
    private Station station;
    @SerializedName("SupportRemoteConfig")
    private Boolean supportRemoteConfig;
    @SerializedName("SupportSound")
    private Boolean supportSound;
    @SerializedName("Version")
    private String version;

    public Long getBattery() {
        return battery;
    }

    public void setBattery(Long battery) {
        this.battery = battery;
    }

    public Long getCardRecordCount() {
        return cardRecordCount;
    }

    public void setCardRecordCount(Long cardRecordCount) {
        this.cardRecordCount = cardRecordCount;
    }

    public Object getCustomerConfig() {
        return customerConfig;
    }

    public void setCustomerConfig(Object customerConfig) {
        this.customerConfig = customerConfig;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getHitRecordCount() {
        return hitRecordCount;
    }

    public void setHitRecordCount(Long hitRecordCount) {
        this.hitRecordCount = hitRecordCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsBatteryLow() {
        return isBatteryLow;
    }

    public void setIsBatteryLow(Boolean isBatteryLow) {
        this.isBatteryLow = isBatteryLow;
    }

    public Boolean getIsHitTooMuch() {
        return isHitTooMuch;
    }

    public void setIsHitTooMuch(Boolean isHitTooMuch) {
        this.isHitTooMuch = isHitTooMuch;
    }

    public Object getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(Object lastHitTime) {
        this.lastHitTime = lastHitTime;
    }

    public String getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(String lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    public String getSIM() {
        return sIM;
    }

    public void setSIM(String sIM) {
        this.sIM = sIM;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Boolean getSupportRemoteConfig() {
        return supportRemoteConfig;
    }

    public void setSupportRemoteConfig(Boolean supportRemoteConfig) {
        this.supportRemoteConfig = supportRemoteConfig;
    }

    public Boolean getSupportSound() {
        return supportSound;
    }

    public void setSupportSound(Boolean supportSound) {
        this.supportSound = supportSound;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
