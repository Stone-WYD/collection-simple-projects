
package com.jgdsun.qms.model.ims4.detail;

import javax.annotation.Generated;
import org.noear.snack.annotation.ONodeAttr;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Property {


    private Long id;

    private String model;
    @ONodeAttr(name="os_name")
    private String osName;
    @ONodeAttr(name="os_version")
    private String osVersion;
    @ONodeAttr(name="physical_model")
    private String physicalModel;
    @ONodeAttr(name="platform_name")
    private String platformName;

    private String type;

    private String vendor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPhysicalModel() {
        return physicalModel;
    }

    public void setPhysicalModel(String physicalModel) {
        this.physicalModel = physicalModel;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

}
