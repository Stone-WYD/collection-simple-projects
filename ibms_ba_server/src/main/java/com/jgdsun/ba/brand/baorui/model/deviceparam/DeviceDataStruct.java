
package com.jgdsun.ba.brand.baorui.model.deviceparam;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DeviceDataStruct {

    @Expose
    private String data;
    @Expose
    private Long dataQuality;
    @Expose
    private Object formula;
    @Expose
    private String identifier;
/*
    @Expose
    private Boolean rawData;
*/
    @Expose
    private Long timestamp;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Long dataQuality) {
        this.dataQuality = dataQuality;
    }

    public Object getFormula() {
        return formula;
    }

    public void setFormula(Object formula) {
        this.formula = formula;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

/*    public Boolean getRawData() {
        return rawData;
    }

    public void setRawData(Boolean rawData) {
        this.rawData = rawData;
    }*/

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
