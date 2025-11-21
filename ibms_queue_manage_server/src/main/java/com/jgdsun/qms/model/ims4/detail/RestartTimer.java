
package com.jgdsun.qms.model.ims4.detail;

import java.util.List;
 

@SuppressWarnings("unused")
public class RestartTimer {


    private Boolean on;

    private Long priority;

    private List<Timer> timers;

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public List<Timer> getTimers() {
        return timers;
    }

    public void setTimers(List<Timer> timers) {
        this.timers = timers;
    }

}
