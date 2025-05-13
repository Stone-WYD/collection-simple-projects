package com.jgdsun.ba.utils.modbus;

/*
 * @Description: moubus通讯命令类
 * @Author: ws
 * @Date: 2022/3/26.
 */


public class ModbusCommandBean {



    /**设备类型 1ups 2空调  3氢气  4水浸  5报警设备  6烟雾 7温度 8湿度 */
    private int equipmentType = 1;

    /**设备id 对应设备表中的 id */
    private String equipmentId = "";

    /**类型 1读 2写*/
    private int gnType = 1;

    /**modbusIP时使用*/
    private String ip;

    /**站号*/
    private int slaveId;

    /**功能吗 对应 modbus 01 02 03 04 05*/
    private int function;

    /**地址*/
    private int address;
    /**读取多少个长度 当为03时表示读取多少个字*/
    private int numberOfBits;

    /**写入的数据类型*/
    private int dataType;
    /**数据*/
    private short dataValue[];

    private int errorTimes = 0;


    private int errorTimes2 = 0;

    /**线圈值*/
    private boolean coilsValue;

    public int getGnType() {
        return gnType;
    }

    public void setGnType(int gnType) {
        this.gnType = gnType;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getNumberOfBits() {
        return numberOfBits;
    }

    public void setNumberOfBits(int numberOfBits) {
        this.numberOfBits = numberOfBits;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public short[] getDataValue() {
        return dataValue;
    }

    public void setDataValue(short[] dataValue) {
        this.dataValue = dataValue;
    }

    public int getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(int errorTimes) {
        this.errorTimes = errorTimes;
    }

    public boolean isCoilsValue() {
        return coilsValue;
    }

    public void setCoilsValue(boolean coilsValue) {
        this.coilsValue = coilsValue;
    }

    public int getErrorTimes2() {
        return errorTimes2;
    }

    public void setErrorTimes2(int errorTimes2) {
        this.errorTimes2 = errorTimes2;
    }

    public int getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(int equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "slaveId "+slaveId+" address "+address+" gnType "+gnType;
    }
}
