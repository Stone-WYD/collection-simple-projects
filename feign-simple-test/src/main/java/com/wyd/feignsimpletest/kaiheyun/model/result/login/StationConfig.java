
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StationConfig {

    @SerializedName("BigScreenConfig")
    private BigScreenConfig bigScreenConfig;
    @SerializedName("BlazorMenuItemConfigs")
    private List<Object> blazorMenuItemConfigs;
    @SerializedName("EnablePhoneNumberVerification")
    private Boolean enablePhoneNumberVerification;
    @SerializedName("FaceVerificationValidMinutes")
    private Long faceVerificationValidMinutes;
    @SerializedName("FinalStationTitle")
    private Object finalStationTitle;
    @SerializedName("IsEnableFaceVerification")
    private Boolean isEnableFaceVerification;
    @SerializedName("IsForceUseNFC")
    private Boolean isForceUseNFC;
    @SerializedName("NormalStationTitle")
    private Object normalStationTitle;
    @SerializedName("PatrollerRecordsQueryPermission")
    private Boolean patrollerRecordsQueryPermission;
    @SerializedName("PatrollerResultsQueryPermission")
    private Boolean patrollerResultsQueryPermission;
    @SerializedName("RepeatCardSaveIntervals")
    private Long repeatCardSaveIntervals;
    @SerializedName("TopStationTitle")
    private Object topStationTitle;
    @SerializedName("WebAppName")
    private Object webAppName;

    public BigScreenConfig getBigScreenConfig() {
        return bigScreenConfig;
    }

    public void setBigScreenConfig(BigScreenConfig bigScreenConfig) {
        this.bigScreenConfig = bigScreenConfig;
    }

    public List<Object> getBlazorMenuItemConfigs() {
        return blazorMenuItemConfigs;
    }

    public void setBlazorMenuItemConfigs(List<Object> blazorMenuItemConfigs) {
        this.blazorMenuItemConfigs = blazorMenuItemConfigs;
    }

    public Boolean getEnablePhoneNumberVerification() {
        return enablePhoneNumberVerification;
    }

    public void setEnablePhoneNumberVerification(Boolean enablePhoneNumberVerification) {
        this.enablePhoneNumberVerification = enablePhoneNumberVerification;
    }

    public Long getFaceVerificationValidMinutes() {
        return faceVerificationValidMinutes;
    }

    public void setFaceVerificationValidMinutes(Long faceVerificationValidMinutes) {
        this.faceVerificationValidMinutes = faceVerificationValidMinutes;
    }

    public Object getFinalStationTitle() {
        return finalStationTitle;
    }

    public void setFinalStationTitle(Object finalStationTitle) {
        this.finalStationTitle = finalStationTitle;
    }

    public Boolean getIsEnableFaceVerification() {
        return isEnableFaceVerification;
    }

    public void setIsEnableFaceVerification(Boolean isEnableFaceVerification) {
        this.isEnableFaceVerification = isEnableFaceVerification;
    }

    public Boolean getIsForceUseNFC() {
        return isForceUseNFC;
    }

    public void setIsForceUseNFC(Boolean isForceUseNFC) {
        this.isForceUseNFC = isForceUseNFC;
    }

    public Object getNormalStationTitle() {
        return normalStationTitle;
    }

    public void setNormalStationTitle(Object normalStationTitle) {
        this.normalStationTitle = normalStationTitle;
    }

    public Boolean getPatrollerRecordsQueryPermission() {
        return patrollerRecordsQueryPermission;
    }

    public void setPatrollerRecordsQueryPermission(Boolean patrollerRecordsQueryPermission) {
        this.patrollerRecordsQueryPermission = patrollerRecordsQueryPermission;
    }

    public Boolean getPatrollerResultsQueryPermission() {
        return patrollerResultsQueryPermission;
    }

    public void setPatrollerResultsQueryPermission(Boolean patrollerResultsQueryPermission) {
        this.patrollerResultsQueryPermission = patrollerResultsQueryPermission;
    }

    public Long getRepeatCardSaveIntervals() {
        return repeatCardSaveIntervals;
    }

    public void setRepeatCardSaveIntervals(Long repeatCardSaveIntervals) {
        this.repeatCardSaveIntervals = repeatCardSaveIntervals;
    }

    public Object getTopStationTitle() {
        return topStationTitle;
    }

    public void setTopStationTitle(Object topStationTitle) {
        this.topStationTitle = topStationTitle;
    }

    public Object getWebAppName() {
        return webAppName;
    }

    public void setWebAppName(Object webAppName) {
        this.webAppName = webAppName;
    }

}
