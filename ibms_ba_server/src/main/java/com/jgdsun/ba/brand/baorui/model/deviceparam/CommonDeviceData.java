
package com.jgdsun.ba.brand.baorui.model.deviceparam;

import java.util.List;
 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class CommonDeviceData {

    @Expose
    private String commonDeviceId;
    @Expose
    private String createTime;
    /*@Expose 重复属性属性，注释掉，减轻内存压力
    private String deviceData;*/
    @Expose
    private List<DeviceDataStruct> deviceDataStruct;
    @Expose
    private String id;
    @Expose
    private Long isDeleted;
    @Expose
    private String lastUpdateTime;
    @Expose
    private String uploadTime;

    public String getCommonDeviceId() {
        return commonDeviceId;
    }

    public void setCommonDeviceId(String commonDeviceId) {
        this.commonDeviceId = commonDeviceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

/*    public String getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(String deviceData) {
        this.deviceData = deviceData;
    }*/

    public List<DeviceDataStruct> getDeviceDataStruct() {
        return deviceDataStruct;
    }

    public void setDeviceDataStruct(List<DeviceDataStruct> deviceDataStruct) {
        this.deviceDataStruct = deviceDataStruct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

}
