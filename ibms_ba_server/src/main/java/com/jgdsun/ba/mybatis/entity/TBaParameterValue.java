package com.jgdsun.ba.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * BA数据
 * @TableName t_ba_parameter_value
 */
public class TBaParameterValue implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 设备ID
     */
    private String parameterId;

    /**
     * 当前值
     */
    private String value;

    /**
     * 添加时间
     */
    private Date addtime;

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
     * 设备ID
     */
    public String getParameterId() {
        return parameterId;
    }

    /**
     * 设备ID
     */
    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    /**
     * 当前值
     */
    public String getValue() {
        return value;
    }

    /**
     * 当前值
     */
    public void setValue(String value) {
        this.value = value;
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
        TBaParameterValue other = (TBaParameterValue) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParameterId() == null ? other.getParameterId() == null : this.getParameterId().equals(other.getParameterId()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getAddtime() == null ? other.getAddtime() == null : this.getAddtime().equals(other.getAddtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParameterId() == null) ? 0 : getParameterId().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getAddtime() == null) ? 0 : getAddtime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parameterId=").append(parameterId);
        sb.append(", value=").append(value);
        sb.append(", addtime=").append(addtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}