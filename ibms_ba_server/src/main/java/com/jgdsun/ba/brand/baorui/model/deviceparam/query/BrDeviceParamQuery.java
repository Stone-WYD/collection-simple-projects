
package com.jgdsun.ba.brand.baorui.model.deviceparam.query;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class BrDeviceParamQuery {

    @Expose
    private String subsystemId;

    @Expose
    private String id;

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
