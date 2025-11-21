
package com.jgdsun.qms.model.ims4.detail;

import javax.annotation.Generated;
import org.noear.snack.annotation.ONodeAttr;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Synch {

    @ONodeAttr(name="multicast_ip")
    private String multicastIp;
    @ONodeAttr(name="multicast_port")
    private Long multicastPort;

    private Boolean on;
    @ONodeAttr(name="set_id")
    private String setId;

    private Long timeout;

    public String getMulticastIp() {
        return multicastIp;
    }

    public void setMulticastIp(String multicastIp) {
        this.multicastIp = multicastIp;
    }

    public Long getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(Long multicastPort) {
        this.multicastPort = multicastPort;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

}
