
package com.jgdsun.qms.model.ims4.detail;

import org.noear.snack.annotation.ONodeAttr;

@SuppressWarnings("unused")
public class Config {


    private Area area;
    @ONodeAttr(name="auto_snapshot")
    private AutoSnapshot autoSnapshot;

    private Debug debug;
    @ONodeAttr(name="debug_url")
    private String debugUrl;
    @ONodeAttr(name="download_segment")
    private DownloadSegment downloadSegment;
    @ONodeAttr(name="exception_tips")
    private Boolean exceptionTips;

    private Extension extension;
    @ONodeAttr(name="heartbeat_interval")
    private Long heartbeatInterval;

    private Long id;
    @ONodeAttr(name="log_url")
    private String logUrl;
    @ONodeAttr(name="main_server")
    private String mainServer;
    @ONodeAttr(name="network_watchdog")
    private Boolean networkWatchdog;
    @ONodeAttr(name="output_mode")
    private String outputMode;

    private String password;

    private Boolean peripheral;
    @ONodeAttr(name="remote_adb")
    private Boolean remoteAdb;
    @ONodeAttr(name="restart_timer")
    private RestartTimer restartTimer;

    private Rotate rotate;
    @ONodeAttr(name="snapshot_url")
    private String snapshotUrl;

    private Synch synch;
    @ONodeAttr(name="upgrade_url")
    private String upgradeUrl;
    @ONodeAttr(name="url_params")
    private UrlParams urlParams;

    private Long version;

    private Long volume;
    @ONodeAttr(name="work_segment")
    private WorkSegment workSegment;
    @ONodeAttr(name="workoff_segment")
    private WorkoffSegment workoffSegment;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public AutoSnapshot getAutoSnapshot() {
        return autoSnapshot;
    }

    public void setAutoSnapshot(AutoSnapshot autoSnapshot) {
        this.autoSnapshot = autoSnapshot;
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public String getDebugUrl() {
        return debugUrl;
    }

    public void setDebugUrl(String debugUrl) {
        this.debugUrl = debugUrl;
    }

    public DownloadSegment getDownloadSegment() {
        return downloadSegment;
    }

    public void setDownloadSegment(DownloadSegment downloadSegment) {
        this.downloadSegment = downloadSegment;
    }

    public Boolean getExceptionTips() {
        return exceptionTips;
    }

    public void setExceptionTips(Boolean exceptionTips) {
        this.exceptionTips = exceptionTips;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public Long getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(Long heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getMainServer() {
        return mainServer;
    }

    public void setMainServer(String mainServer) {
        this.mainServer = mainServer;
    }

    public Boolean getNetworkWatchdog() {
        return networkWatchdog;
    }

    public void setNetworkWatchdog(Boolean networkWatchdog) {
        this.networkWatchdog = networkWatchdog;
    }

    public String getOutputMode() {
        return outputMode;
    }

    public void setOutputMode(String outputMode) {
        this.outputMode = outputMode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPeripheral() {
        return peripheral;
    }

    public void setPeripheral(Boolean peripheral) {
        this.peripheral = peripheral;
    }

    public Boolean getRemoteAdb() {
        return remoteAdb;
    }

    public void setRemoteAdb(Boolean remoteAdb) {
        this.remoteAdb = remoteAdb;
    }

    public RestartTimer getRestartTimer() {
        return restartTimer;
    }

    public void setRestartTimer(RestartTimer restartTimer) {
        this.restartTimer = restartTimer;
    }

    public Rotate getRotate() {
        return rotate;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public Synch getSynch() {
        return synch;
    }

    public void setSynch(Synch synch) {
        this.synch = synch;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public UrlParams getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(UrlParams urlParams) {
        this.urlParams = urlParams;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public WorkSegment getWorkSegment() {
        return workSegment;
    }

    public void setWorkSegment(WorkSegment workSegment) {
        this.workSegment = workSegment;
    }

    public WorkoffSegment getWorkoffSegment() {
        return workoffSegment;
    }

    public void setWorkoffSegment(WorkoffSegment workoffSegment) {
        this.workoffSegment = workoffSegment;
    }

}
