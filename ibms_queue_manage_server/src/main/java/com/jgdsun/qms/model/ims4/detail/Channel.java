
package com.jgdsun.qms.model.ims4.detail;

import javax.annotation.Generated;
import org.noear.snack.annotation.ONodeAttr;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Channel {


    private Long audit;

    private Boolean changed;
     @ONodeAttr(name="create_time")
    private String createTime;
     @ONodeAttr(name="effect_time")
    private String effectTime;

    private Long id;

    private String name;

    private String order;
     @ONodeAttr(name="publish_time")
    private String publishTime;

    private String publisher;

    private String type;

    private Boolean valid;

    private Long version;

    public Long getAudit() {
        return audit;
    }

    public void setAudit(Long audit) {
        this.audit = audit;
    }

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
