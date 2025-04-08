
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StationInfo {

    @SerializedName("Address")
    private String address;
    @SerializedName("CanUploadFromAlbum")
    private Boolean canUploadFromAlbum;
    @SerializedName("City")
    private String city;
    @SerializedName("CreateDate")
    private String createDate;
    @SerializedName("ExpiredDate")
    private String expiredDate;
    @SerializedName("ExpiredTips")
    private Object expiredTips;
    @SerializedName("IsEnableVirtualPlace")
    private Boolean isEnableVirtualPlace;
    @SerializedName("IsForcePhoneNumberVerification")
    private Boolean isForcePhoneNumberVerification;
    @SerializedName("IsMobilePatrol")
    private Boolean isMobilePatrol;
    @SerializedName("IsRFID")
    private Boolean isRFID;
    @SerializedName("IsReminderPayment")
    private Boolean isReminderPayment;
    @SerializedName("IsTrial")
    private Boolean isTrial;
    @SerializedName("Linkman")
    private String linkman;
    @SerializedName("NotLimitAtFinalStation")
    private Boolean notLimitAtFinalStation;
    @SerializedName("Note")
    private Object note;
    @SerializedName("PatrolRecordSaveIntervals")
    private Long patrolRecordSaveIntervals;
    @SerializedName("PaymentTelephone")
    private Object paymentTelephone;
    @SerializedName("PhoneNumberVerificationRemainder")
    private Long phoneNumberVerificationRemainder;
    @SerializedName("Province")
    private String province;
    @SerializedName("StationConfig")
    private StationConfig stationConfig;
    @SerializedName("StationFeatureConfig")
    private StationFeatureConfig stationFeatureConfig;
    @SerializedName("SupportProductType")
    private Long supportProductType;
    @SerializedName("SupportTcpDevice")
    private Boolean supportTcpDevice;
    @SerializedName("Telephone")
    private String telephone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getCanUploadFromAlbum() {
        return canUploadFromAlbum;
    }

    public void setCanUploadFromAlbum(Boolean canUploadFromAlbum) {
        this.canUploadFromAlbum = canUploadFromAlbum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Object getExpiredTips() {
        return expiredTips;
    }

    public void setExpiredTips(Object expiredTips) {
        this.expiredTips = expiredTips;
    }

    public Boolean getIsEnableVirtualPlace() {
        return isEnableVirtualPlace;
    }

    public void setIsEnableVirtualPlace(Boolean isEnableVirtualPlace) {
        this.isEnableVirtualPlace = isEnableVirtualPlace;
    }

    public Boolean getIsForcePhoneNumberVerification() {
        return isForcePhoneNumberVerification;
    }

    public void setIsForcePhoneNumberVerification(Boolean isForcePhoneNumberVerification) {
        this.isForcePhoneNumberVerification = isForcePhoneNumberVerification;
    }

    public Boolean getIsMobilePatrol() {
        return isMobilePatrol;
    }

    public void setIsMobilePatrol(Boolean isMobilePatrol) {
        this.isMobilePatrol = isMobilePatrol;
    }

    public Boolean getIsRFID() {
        return isRFID;
    }

    public void setIsRFID(Boolean isRFID) {
        this.isRFID = isRFID;
    }

    public Boolean getIsReminderPayment() {
        return isReminderPayment;
    }

    public void setIsReminderPayment(Boolean isReminderPayment) {
        this.isReminderPayment = isReminderPayment;
    }

    public Boolean getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(Boolean isTrial) {
        this.isTrial = isTrial;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Boolean getNotLimitAtFinalStation() {
        return notLimitAtFinalStation;
    }

    public void setNotLimitAtFinalStation(Boolean notLimitAtFinalStation) {
        this.notLimitAtFinalStation = notLimitAtFinalStation;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Long getPatrolRecordSaveIntervals() {
        return patrolRecordSaveIntervals;
    }

    public void setPatrolRecordSaveIntervals(Long patrolRecordSaveIntervals) {
        this.patrolRecordSaveIntervals = patrolRecordSaveIntervals;
    }

    public Object getPaymentTelephone() {
        return paymentTelephone;
    }

    public void setPaymentTelephone(Object paymentTelephone) {
        this.paymentTelephone = paymentTelephone;
    }

    public Long getPhoneNumberVerificationRemainder() {
        return phoneNumberVerificationRemainder;
    }

    public void setPhoneNumberVerificationRemainder(Long phoneNumberVerificationRemainder) {
        this.phoneNumberVerificationRemainder = phoneNumberVerificationRemainder;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public StationConfig getStationConfig() {
        return stationConfig;
    }

    public void setStationConfig(StationConfig stationConfig) {
        this.stationConfig = stationConfig;
    }

    public StationFeatureConfig getStationFeatureConfig() {
        return stationFeatureConfig;
    }

    public void setStationFeatureConfig(StationFeatureConfig stationFeatureConfig) {
        this.stationFeatureConfig = stationFeatureConfig;
    }

    public Long getSupportProductType() {
        return supportProductType;
    }

    public void setSupportProductType(Long supportProductType) {
        this.supportProductType = supportProductType;
    }

    public Boolean getSupportTcpDevice() {
        return supportTcpDevice;
    }

    public void setSupportTcpDevice(Boolean supportTcpDevice) {
        this.supportTcpDevice = supportTcpDevice;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
