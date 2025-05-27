package org.wyd.back.bean;

import java.sql.Timestamp;

/**
 * @author ws
 * @date 2021-10-29
 * @Description: 监控摄像头
 */
public class MonitorCamera {

    private String id;

    private String storeyId;


    private String name;

    private String ip;

    private int port;

    private String cameraId;

    private String username;

    private String password;

    private String videoPath;

    private String ctrlFlag;

    private String mainpageShow;


    private int positionX;
    private int positionY;

    private int positionZ;

    private int rotationX;
    private int rotationY;
    private int rotationZ;
    private int sortNo;

    /**添加时间*/
    private Timestamp addtime;

    private String enable;

    private String stat;

    /**0离线 1在线*/
    private String status;//

    //-辅助使用
    private String buildId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreyId() {
        return storeyId;
    }

    public void setStoreyId(String storeyId) {
        this.storeyId = storeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getCtrlFlag() {
        return ctrlFlag;
    }

    public void setCtrlFlag(String ctrlFlag) {
        this.ctrlFlag = ctrlFlag;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
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

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getMainpageShow() {
        return mainpageShow;
    }

    public void setMainpageShow(String mainpageShow) {
        this.mainpageShow = mainpageShow;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(int positionZ) {
        this.positionZ = positionZ;
    }
    public int getRotationX() {
        return rotationX;
    }

    public void setRotationX(int rotationX) {
        this.rotationX = rotationX;
    }

    public int getRotationY() {
        return rotationY;
    }

    public void setRotationY(int rotationY) {
        this.rotationY = rotationY;
    }

    public int getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(int rotationZ) {
        this.rotationZ = rotationZ;
    }
}
