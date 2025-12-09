
package com.jgdsun.qms.model.ims4.detail;

import org.noear.snack.annotation.ONodeAttr;

@SuppressWarnings("unused")
public class Category {

    @ONodeAttr(name="create_time")
    private String createTime;

    private Long id;

    private String name;

    private String type;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
