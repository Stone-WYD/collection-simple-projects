
package com.jgdsun.ba.brand.baorui.model.deviceparam;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class DataPropertyList {

    @Expose
    private String accessMode;
    @Expose
    private Object data;
    @Expose
    private Long dataQuality;
    @Expose
    private String identifier;
    @Expose
    private String name;
    @Expose
    private Specs specs;
    @Expose
    private Long timestamp;
    @Expose
    private String type;
    @Expose
    private ValuesConfig valuesConfig;

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Long dataQuality) {
        this.dataQuality = dataQuality;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specs getSpecs() {
        return specs;
    }

    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValuesConfig getValuesConfig() {
        return valuesConfig;
    }

    public void setValuesConfig(ValuesConfig valuesConfig) {
        this.valuesConfig = valuesConfig;
    }

}
