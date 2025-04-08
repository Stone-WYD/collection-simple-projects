
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Station {

    @SerializedName("ChildStations")
    private List<ChildStation> childStations;
    @SerializedName("Code")
    private String code;
    @SerializedName("CreateDate")
    private Object createDate;
    @SerializedName("ExpiredDate")
    private String expiredDate;
    @SerializedName("IsFinalStation")
    private Boolean isFinalStation;
    @SerializedName("Level")
    private Long level;
    @SerializedName("MaxLevel")
    private Long maxLevel;
    @SerializedName("Name")
    private String name;
    @SerializedName("NotLimitAtFinalStation")
    private Object notLimitAtFinalStation;
    @SerializedName("ParentStationId")
    private Object parentStationId;
    @SerializedName("PatrolDetailType")
    private Long patrolDetailType;
    @SerializedName("SortOrder")
    private Object sortOrder;
    @SerializedName("StationId")
    private Long stationId;
    @SerializedName("StationInfo")
    private StationInfo stationInfo;
    @SerializedName("SupportProductType")
    private Long supportProductType;

    public List<ChildStation> getChildStations() {
        return childStations;
    }

    public void setChildStations(List<ChildStation> childStations) {
        this.childStations = childStations;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getIsFinalStation() {
        return isFinalStation;
    }

    public void setIsFinalStation(Boolean isFinalStation) {
        this.isFinalStation = isFinalStation;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Long maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNotLimitAtFinalStation() {
        return notLimitAtFinalStation;
    }

    public void setNotLimitAtFinalStation(Object notLimitAtFinalStation) {
        this.notLimitAtFinalStation = notLimitAtFinalStation;
    }

    public Object getParentStationId() {
        return parentStationId;
    }

    public void setParentStationId(Object parentStationId) {
        this.parentStationId = parentStationId;
    }

    public Long getPatrolDetailType() {
        return patrolDetailType;
    }

    public void setPatrolDetailType(Long patrolDetailType) {
        this.patrolDetailType = patrolDetailType;
    }

    public Object getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Object sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public StationInfo getStationInfo() {
        return stationInfo;
    }

    public void setStationInfo(StationInfo stationInfo) {
        this.stationInfo = stationInfo;
    }

    public Long getSupportProductType() {
        return supportProductType;
    }

    public void setSupportProductType(Long supportProductType) {
        this.supportProductType = supportProductType;
    }

}
