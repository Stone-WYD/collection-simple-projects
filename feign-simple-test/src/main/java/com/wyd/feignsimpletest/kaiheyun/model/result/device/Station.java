
package com.wyd.feignsimpletest.kaiheyun.model.result.device;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Station {

    @SerializedName("Code")
    private String code;
    @SerializedName("IsFinalStation")
    private Boolean isFinalStation;
    @SerializedName("Level")
    private Long level;
    @SerializedName("MaxLevel")
    private Long maxLevel;
    @SerializedName("Name")
    private String name;
    @SerializedName("ParentStationId")
    private Long parentStationId;
    @SerializedName("SortOrder")
    private Object sortOrder;
    @SerializedName("StationId")
    private Long stationId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getParentStationId() {
        return parentStationId;
    }

    public void setParentStationId(Long parentStationId) {
        this.parentStationId = parentStationId;
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

}
