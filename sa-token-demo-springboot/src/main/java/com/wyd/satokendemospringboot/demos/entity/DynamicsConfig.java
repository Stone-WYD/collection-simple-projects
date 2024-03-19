package com.wyd.satokendemospringboot.demos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (DynamicsConfig)表实体类
 *
 * @author Stone
 * @since 2023-07-19 11:54:45
 */
@SuppressWarnings("serial")
public class DynamicsConfig extends Model<DynamicsConfig> {
    //主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    //配置名
    private String name;
    //配置最后一段英文名
    private String code;
    //配置完整code，即在配置文件中的路径
    private String completeCode;
    //配置值
    private String value;
    //配置含义描述
    private String commentary;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
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

