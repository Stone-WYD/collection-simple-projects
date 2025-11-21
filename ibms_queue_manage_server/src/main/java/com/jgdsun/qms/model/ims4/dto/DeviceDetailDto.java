
package com.jgdsun.qms.model.ims4.dto;

import org.noear.snack.annotation.ONodeAttr;

@SuppressWarnings("unused")
public class DeviceDetailDto {


    private Long number;
    @ONodeAttr(name="order_by")
    private String orderBy;

    private Long size;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

}
