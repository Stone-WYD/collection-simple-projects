package com.wyd.asset_manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Asset)表实体类
 *
 * @author makejava
 * @since 2024-03-19 10:56:55
 */
public class Asset extends Model<Asset> {
//主键
    @TableId(type = IdType.AUTO)
    private Long id;
//项目名称
    private String projectName;
//客户名称
    private String customName;
//项目状态：1. 在保，0. 过保
    private Integer projectStatus;
//项目开始时间
    private LocalDateTime beginTime;
//项目结束时间
    private LocalDateTime endTime;
//是否存在应收账款：1.存在，0.不存在
    private Integer accountsReceivable;
//应收账款金额 
    private Double amountReceivable;
//合同额
    private Double amountContract;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getAccountsReceivable() {
        return accountsReceivable;
    }

    public void setAccountsReceivable(Integer accountsReceivable) {
        this.accountsReceivable = accountsReceivable;
    }

    public Double getAmountReceivable() {
        return amountReceivable;
    }

    public void setAmountReceivable(Double amountReceivable) {
        this.amountReceivable = amountReceivable;
    }

    public Double getAmountContract() {
        return amountContract;
    }

    public void setAmountContract(Double amountContract) {
        this.amountContract = amountContract;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

