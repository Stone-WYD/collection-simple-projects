
package com.jgdsun.qms.model.ims4;

import javax.annotation.Generated;

import com.jgdsun.qms.model.ims4.detail.ListInfo;
import com.jgdsun.qms.model.ims4.detail.StatusStats;
import com.jgdsun.qms.model.ims4.detail.VersionStat;
import org.noear.snack.annotation.ONodeAttr;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DeviceDetail {


    private List<ListInfo> list;
    @ONodeAttr(name="status_stats")
    private StatusStats statusStats;

    private Long total;
    @ONodeAttr(name="version_stats")
    private List<VersionStat> versionStats;

    public List<ListInfo> getList() {
        return list;
    }

    public void setList(List<ListInfo> list) {
        this.list = list;
    }

    public StatusStats getStatusStats() {
        return statusStats;
    }

    public void setStatusStats(StatusStats statusStats) {
        this.statusStats = statusStats;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<VersionStat> getVersionStats() {
        return versionStats;
    }

    public void setVersionStats(List<VersionStat> versionStats) {
        this.versionStats = versionStats;
    }

}
