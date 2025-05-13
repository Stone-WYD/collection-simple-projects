package com.jgdsun.ba.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BA参数
 * @TableName t_ba_parameter
 */
@Data
public class TBaParameter implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 设备ID
     */
    private String equipmentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 对象名称
     */
    private String obName;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 设备ID
     */
    private String devid;

    /**
     * 索引
     */
    private Integer baIndex;

    /**
     * BV  AV
     */
    private String type;

    /**
     * BV  AV
     */
    private String ioType;

    /**
     * BV时的选择项  逗号分割
     */
    private String options;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否可写 0 只读 1读写
     */
    private String writeAble;

    /**
     * 当前值
     */
    private String lastvalue;

    /**
     * 最后更新时间
     */
    private Date lasttime;

    /**
     * 小数位数
     */
    private Integer digit;

    /**
     * 是否时长统计 只适用用BV BI，  0不统计 1 为0触发统计  2为1触发统计 3双向触发
     */
    private String durationStatistics;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     *
     */
    private String stat;

    /**
     * 0正常无需处理 1转二进制后索引值(如8 转二进制1000 值为4  2转二进制位 10 值为1)
     */
    @TableField(exist = false)
    private Integer dataType;

    /**
     * modbus 模式时表示此属性使用的字长度
     */
    @TableField(exist = false)
    private Integer dataLen;

    /**
     * modbus 模式时的数据系数 1不处理  100表示需/100
     */
    @TableField(exist = false)
    private Integer dataCoefficient;

    @TableField(exist = false)
    private String protocol;

    /**
     * data_type == 20时使用 数据映射如索引值对应 1|4|8|16|22
     */
    @TableField(exist = false)
    private String dataMaping;


    private static final long serialVersionUID = 1L;

}