package com.jgdsun.ba.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * BA
 * @TableName t_ba
 */
public class TBa implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 楼层ID
     */
    private String storeyId;

    /**
     * 设备类型
     */
    private String equipmentTypeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 通讯协议 ,0 modbus, 1 tcp/ip , 2 http .....
     */
    private Integer protocol;

    /**
     * 设备通讯地址，通讯中的设备标识
     */
    private String address;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 效果图坐标X
     */
    private Integer positionX;

    /**
     * 效果图坐标Y
     */
    private Integer positionY;

    /**
     * 是否启用0不启用1启用
     */
    private String enable;

    /**
     * 当前状态0未知 1正常 2异常
     */
    private String nowstatus;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 
     */
    private String stat;

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 楼层ID
     */
    public String getStoreyId() {
        return storeyId;
    }

    /**
     * 楼层ID
     */
    public void setStoreyId(String storeyId) {
        this.storeyId = storeyId;
    }

    /**
     * 设备类型
     */
    public String getEquipmentTypeId() {
        return equipmentTypeId;
    }

    /**
     * 设备类型
     */
    public void setEquipmentTypeId(String equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 通讯协议 ,0 modbus, 1 tcp/ip , 2 http .....
     */
    public Integer getProtocol() {
        return protocol;
    }

    /**
     * 通讯协议 ,0 modbus, 1 tcp/ip , 2 http .....
     */
    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    /**
     * 设备通讯地址，通讯中的设备标识
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设备通讯地址，通讯中的设备标识
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * IP地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 排序
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 排序
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 效果图坐标X
     */
    public Integer getPositionX() {
        return positionX;
    }

    /**
     * 效果图坐标X
     */
    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    /**
     * 效果图坐标Y
     */
    public Integer getPositionY() {
        return positionY;
    }

    /**
     * 效果图坐标Y
     */
    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    /**
     * 是否启用0不启用1启用
     */
    public String getEnable() {
        return enable;
    }

    /**
     * 是否启用0不启用1启用
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     * 当前状态0未知 1正常 2异常
     */
    public String getNowstatus() {
        return nowstatus;
    }

    /**
     * 当前状态0未知 1正常 2异常
     */
    public void setNowstatus(String nowstatus) {
        this.nowstatus = nowstatus;
    }

    /**
     * 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * 
     */
    public String getStat() {
        return stat;
    }

    /**
     * 
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TBa other = (TBa) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStoreyId() == null ? other.getStoreyId() == null : this.getStoreyId().equals(other.getStoreyId()))
            && (this.getEquipmentTypeId() == null ? other.getEquipmentTypeId() == null : this.getEquipmentTypeId().equals(other.getEquipmentTypeId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getProtocol() == null ? other.getProtocol() == null : this.getProtocol().equals(other.getProtocol()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getSortNo() == null ? other.getSortNo() == null : this.getSortNo().equals(other.getSortNo()))
            && (this.getPositionX() == null ? other.getPositionX() == null : this.getPositionX().equals(other.getPositionX()))
            && (this.getPositionY() == null ? other.getPositionY() == null : this.getPositionY().equals(other.getPositionY()))
            && (this.getEnable() == null ? other.getEnable() == null : this.getEnable().equals(other.getEnable()))
            && (this.getNowstatus() == null ? other.getNowstatus() == null : this.getNowstatus().equals(other.getNowstatus()))
            && (this.getAddtime() == null ? other.getAddtime() == null : this.getAddtime().equals(other.getAddtime()))
            && (this.getStat() == null ? other.getStat() == null : this.getStat().equals(other.getStat()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStoreyId() == null) ? 0 : getStoreyId().hashCode());
        result = prime * result + ((getEquipmentTypeId() == null) ? 0 : getEquipmentTypeId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getProtocol() == null) ? 0 : getProtocol().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getSortNo() == null) ? 0 : getSortNo().hashCode());
        result = prime * result + ((getPositionX() == null) ? 0 : getPositionX().hashCode());
        result = prime * result + ((getPositionY() == null) ? 0 : getPositionY().hashCode());
        result = prime * result + ((getEnable() == null) ? 0 : getEnable().hashCode());
        result = prime * result + ((getNowstatus() == null) ? 0 : getNowstatus().hashCode());
        result = prime * result + ((getAddtime() == null) ? 0 : getAddtime().hashCode());
        result = prime * result + ((getStat() == null) ? 0 : getStat().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", storeyId=").append(storeyId);
        sb.append(", equipmentTypeId=").append(equipmentTypeId);
        sb.append(", name=").append(name);
        sb.append(", protocol=").append(protocol);
        sb.append(", address=").append(address);
        sb.append(", ip=").append(ip);
        sb.append(", sortNo=").append(sortNo);
        sb.append(", positionX=").append(positionX);
        sb.append(", positionY=").append(positionY);
        sb.append(", enable=").append(enable);
        sb.append(", nowstatus=").append(nowstatus);
        sb.append(", addtime=").append(addtime);
        sb.append(", stat=").append(stat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}