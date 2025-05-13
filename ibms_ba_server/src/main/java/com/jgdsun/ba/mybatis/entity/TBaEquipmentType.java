package com.jgdsun.ba.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * BA设备类型
 * @TableName t_ba_equipment_type
 */
public class TBaEquipmentType implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型码
     */
    private String code;

    /**
     * 0无效1有效
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
     * 类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 类型码
     */
    public String getCode() {
        return code;
    }

    /**
     * 类型码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 0无效1有效
     */
    public String getStat() {
        return stat;
    }

    /**
     * 0无效1有效
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
        TBaEquipmentType other = (TBaEquipmentType) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getStat() == null ? other.getStat() == null : this.getStat().equals(other.getStat()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", stat=").append(stat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}