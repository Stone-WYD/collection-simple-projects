
package com.jgdsun.ba.brand.baorui.model.deviceparam.query.control;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class Property {

    @Expose
    private Object data;
    @Expose
    private String identifier;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
