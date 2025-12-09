
package com.wyd.feignsimpletest.kaiheyun.model.result.login;

import com.google.gson.annotations.SerializedName;

 

  
@SuppressWarnings("unused")
public class BigScreenConfig {

    @SerializedName("IsSateLayer")
    private Object isSateLayer;
    @SerializedName("LocationQueryHours")
    private Long locationQueryHours;
    @SerializedName("StatisticsQueryDays")
    private Long statisticsQueryDays;
    @SerializedName("Title")
    private String title;

    public Object getIsSateLayer() {
        return isSateLayer;
    }

    public void setIsSateLayer(Object isSateLayer) {
        this.isSateLayer = isSateLayer;
    }

    public Long getLocationQueryHours() {
        return locationQueryHours;
    }

    public void setLocationQueryHours(Long locationQueryHours) {
        this.locationQueryHours = locationQueryHours;
    }

    public Long getStatisticsQueryDays() {
        return statisticsQueryDays;
    }

    public void setStatisticsQueryDays(Long statisticsQueryDays) {
        this.statisticsQueryDays = statisticsQueryDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
