package com.jgdsun.ba.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * 参数某状态持续时长
 * @TableName t_ba_parameter_duration
 */
public class TBaParameterDuration implements Serializable {
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
     * 开始时间
     */
    private Date begtime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 时长秒
     */
    private Integer duration;

    /**
     * 记录时的BA值
     */
    private String value;

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
     * 开始时间
     */
    public Date getBegtime() {
        return begtime;
    }

    /**
     * 开始时间
     */
    public void setBegtime(Date begtime) {
        this.begtime = begtime;
    }

    /**
     * 结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 时长秒
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * 时长秒
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * 记录时的BA值
     */
    public String getValue() {
        return value;
    }

    /**
     * 记录时的BA值
     */
    public void setValue(String value) {
        this.value = value;
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
        TBaParameterDuration other = (TBaParameterDuration) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParameterId() == null ? other.getParameterId() == null : this.getParameterId().equals(other.getParameterId()))
            && (this.getBegtime() == null ? other.getBegtime() == null : this.getBegtime().equals(other.getBegtime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParameterId() == null) ? 0 : getParameterId().hashCode());
        result = prime * result + ((getBegtime() == null) ? 0 : getBegtime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
        sb.append(", begtime=").append(begtime);
        sb.append(", endtime=").append(endtime);
        sb.append(", duration=").append(duration);
        sb.append(", value=").append(value);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}