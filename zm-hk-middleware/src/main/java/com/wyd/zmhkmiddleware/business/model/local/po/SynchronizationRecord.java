package com.wyd.zmhkmiddleware.business.model.local.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName synchronization_record
 */
@TableName(value ="synchronization_record")
public class SynchronizationRecord implements Serializable {


    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private String bussinessId;

    /**
     * 
     */
    private Integer syncStatus;

    /**
     * 
     */
    private String syncDate;

    /**
     * 
     */
    private String syncPerson;

    @TableField(value = "extend_1")
    private String extend1;

    /**
     * 
     */
    @TableField(value = "extend_2")
    private String extend2;

    /**
     * 
     */
    @TableField(value = "extend_3")
    private String extend3;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public Integer getType() {
        return type;
    }

    /**
     * 
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 
     */
    public String getBussinessId() {
        return bussinessId;
    }

    /**
     * 
     */
    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    /**
     * 
     */
    public Integer getSyncStatus() {
        return syncStatus;
    }

    /**
     * 
     */
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**
     * 
     */
    public String getSyncDate() {
        return syncDate;
    }

    /**
     * 
     */
    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    /**
     * 
     */
    public String getSyncPerson() {
        return syncPerson;
    }

    /**
     * 
     */
    public void setSyncPerson(String syncPerson) {
        this.syncPerson = syncPerson;
    }

    /**
     * 
     */
    public String getExtend1() {
        return extend1;
    }

    /**
     * 
     */
    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    /**
     * 
     */
    public String getExtend2() {
        return extend2;
    }

    /**
     * 
     */
    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    /**
     * 
     */
    public String getExtend3() {
        return extend3;
    }

    /**
     * 
     */
    public void setExtend3(String extend3) {
        this.extend3 = extend3;
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
        SynchronizationRecord other = (SynchronizationRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getBussinessId() == null ? other.getBussinessId() == null : this.getBussinessId().equals(other.getBussinessId()))
            && (this.getSyncStatus() == null ? other.getSyncStatus() == null : this.getSyncStatus().equals(other.getSyncStatus()))
            && (this.getSyncDate() == null ? other.getSyncDate() == null : this.getSyncDate().equals(other.getSyncDate()))
            && (this.getSyncPerson() == null ? other.getSyncPerson() == null : this.getSyncPerson().equals(other.getSyncPerson()))
            && (this.getExtend1() == null ? other.getExtend1() == null : this.getExtend1().equals(other.getExtend1()))
            && (this.getExtend2() == null ? other.getExtend2() == null : this.getExtend2().equals(other.getExtend2()))
            && (this.getExtend3() == null ? other.getExtend3() == null : this.getExtend3().equals(other.getExtend3()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getBussinessId() == null) ? 0 : getBussinessId().hashCode());
        result = prime * result + ((getSyncStatus() == null) ? 0 : getSyncStatus().hashCode());
        result = prime * result + ((getSyncDate() == null) ? 0 : getSyncDate().hashCode());
        result = prime * result + ((getSyncPerson() == null) ? 0 : getSyncPerson().hashCode());
        result = prime * result + ((getExtend1() == null) ? 0 : getExtend1().hashCode());
        result = prime * result + ((getExtend2() == null) ? 0 : getExtend2().hashCode());
        result = prime * result + ((getExtend3() == null) ? 0 : getExtend3().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", bussinessId=").append(bussinessId);
        sb.append(", syncStatus=").append(syncStatus);
        sb.append(", syncDate=").append(syncDate);
        sb.append(", syncPerson=").append(syncPerson);
        sb.append(", extend1=").append(extend1);
        sb.append(", extend2=").append(extend2);
        sb.append(", extend3=").append(extend3);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}