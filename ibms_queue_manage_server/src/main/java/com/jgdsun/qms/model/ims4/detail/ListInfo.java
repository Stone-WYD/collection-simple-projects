
package com.jgdsun.qms.model.ims4.detail;

import org.noear.snack.annotation.ONodeAttr;

import java.util.List;

@SuppressWarnings("unused")
public class ListInfo {

     @ONodeAttr(name="app_version")
    private String appVersion;
     @ONodeAttr(name="app_version_code")
    private Long appVersionCode;

    private Auth auth;

    private Category category;

    private List<Channel> channels;

    private Config config;
     @ONodeAttr(name="create_time")
    private String createTime;

    private Gps gps;

    private Long height;

    private Long id;
     @ONodeAttr(name="install_rotate")
    private Long installRotate;

    private String ip;

    private List<Label> labels;
     @ONodeAttr(name="last_heartbeat_time")
    private String lastHeartbeatTime;
     @ONodeAttr(name="last_offline_time")
    private String lastOfflineTime;
     @ONodeAttr(name="last_online_time")
    private String lastOnlineTime;
     @ONodeAttr(name="link_type")
    private String linkType;

    private String mac;

    private String name;
     @ONodeAttr(name="osd_rotate")
    private Long osdRotate;

    private Property property;

    /*在线、休眠、离线、后台、总计
    RUNNING、DORMANT、
    OFFLINE、BACKGROUD、
    TOTAL*/
    private String status;

    private Long width;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Long getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(Long appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstallRotate() {
        return installRotate;
    }

    public void setInstallRotate(Long installRotate) {
        this.installRotate = installRotate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void setLastHeartbeatTime(String lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public String getLastOfflineTime() {
        return lastOfflineTime;
    }

    public void setLastOfflineTime(String lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOsdRotate() {
        return osdRotate;
    }

    public void setOsdRotate(Long osdRotate) {
        this.osdRotate = osdRotate;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

}
