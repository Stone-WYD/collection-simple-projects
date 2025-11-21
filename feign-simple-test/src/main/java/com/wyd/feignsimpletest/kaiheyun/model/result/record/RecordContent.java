
package com.wyd.feignsimpletest.kaiheyun.model.result.record;

import com.google.gson.annotations.SerializedName;

 
import java.util.List;

  
@SuppressWarnings("unused")
public class RecordContent {

    @SerializedName("Records")
    private List<Record> records;
    @SerializedName("RouteName")
    private String routeName;
    @SerializedName("StationName")
    private String stationName;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

}
