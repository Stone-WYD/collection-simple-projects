/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jgdsun.ba.modbusip;

/**
 * @Description: ups 功能接口
 * @Author: 王杉
 * @Date: 2024/3/26.
 */

import com.jgdsun.ba.utils.modbus.ModbusCommandBean;

import java.util.List;

public interface ModbusServer {


    /**状态变更推送*/
     String statuschangeTopic = "ba/valuechange";


    /**
     * 获取所有需要读取的command
     * @return
     */
    List<ModbusCommandBean> getAllReadCommand();

    /**
     * 读取命令返回
     * @param slaveId 站号
     * @param address 开启寄存器地址
     * @param data 数据
     */
    void readCommandBack(boolean flag,int slaveId,int address,short data[],int readLen);

    /**
     * 获取写命令
     * @return
     */
    ModbusCommandBean getWriteCommand(int registerAddress,int status);

    /**
     * 读取单寄存器，控制命令发送后的快速读取使用
     * @param registerAddress 寄存器地址

     */
    public ModbusCommandBean getReadCommandOne(int registerAddress,int len);

}
