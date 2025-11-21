
package com.jgdsun.ba.brand.baorui.model.deviceparam.query.control;

import java.util.List;
 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class BrControlQuery {

    @Expose
    private List<String> deviceIds;
    @Expose
    private List<Property> properties;

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
