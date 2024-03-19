package com.wyd.satokendemospringboot.demos.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.wyd.satokendemospringboot.demos.constants.TestIEum;

import java.io.Serializable;

/**
 * (Ieum)表实体类
 *
 * @author makejava
 * @since 2023-06-24 17:03:27
 */
@SuppressWarnings("serial")
public class Ieum extends Model<Ieum> {
    
    private Integer id;
    //物品名称
    private String testName;
    //枚举字典值，1.王玉东，2.叶絮依
    private TestIEum testIeum;
    //物品拥有数量
    private Integer num;
    //物品单价
    private Double price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public TestIEum getTestIeum() {
        return testIeum;
    }

    public void setTestIeum(TestIEum testIeum) {
        this.testIeum = testIeum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

