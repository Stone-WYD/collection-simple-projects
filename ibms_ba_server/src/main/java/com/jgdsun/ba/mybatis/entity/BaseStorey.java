package com.jgdsun.ba.mybatis.entity;

import java.sql.Timestamp;

/**
 * 楼层
 */
public class BaseStorey {

    private String id;

    private String buildId;//楼栋id

    private String name;//名称

    private String showName;//显示名称

    private String storeyDraw;//图片路径

    private String storeyType;//类型 0 地下室 1地上

    private String floor;

    private int sortNo;//排序

    private Timestamp addtime;

    private int imgWidth;
    private int imgHeight;
    private String enable;

    private String stat;

    //----辅助使用
    private String delPicture;//删除的图片

    private String files;//上传的文件名称 多个用,分割

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getStoreyDraw() {
        return storeyDraw;
    }

    public void setStoreyDraw(String storeyDraw) {
        this.storeyDraw = storeyDraw;
    }

    public String getStoreyType() {
        return storeyType;
    }

    public void setStoreyType(String storeyType) {
        this.storeyType = storeyType;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public Timestamp getAddtime() {
        return addtime;
    }

    public void setAddtime(Timestamp addtime) {
        this.addtime = addtime;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDelPicture() {
        return delPicture;
    }

    public void setDelPicture(String delPicture) {
        this.delPicture = delPicture;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
