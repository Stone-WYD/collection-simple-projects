
package com.jgdsun.ba.brand.baorui.model.login.result;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class Version {

    @Expose
    private Long code;
    @Expose
    private String name;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
