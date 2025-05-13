package com.jgdsun.ba.utils.modbus;

/**
 * Copyright (c) 2009-2020 Freedomotic Team http://www.freedomotic-iot.com
 * <p>
 * This file is part of Freedomotic
 * <p>
 * This Program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * <p>
 * This Program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * Freedomotic; see the file COPYING. If not, see
 * <http://www.gnu.org/licenses/>.
 */

import com.serotonin.modbus4j.serial.SerialPortWrapper;
import gnu.io.*;

import java.io.InputStream;
import java.io.OutputStream;


/**
 *
 */
public class SerialPortWrapperImpl implements SerialPortWrapper {


    private SerialPort port = null;
    private String commPortId;
    private int baudRate;
    private int dataBits = SerialPort.DATABITS_8;
    private int stopBits = SerialPort.STOPBITS_1;
    private int parity = SerialPort.PARITY_NONE;
    private int flowControlIn;
    private int flowControlOut;

    /**
     * @param commPortId 串口号
     * @param baudRate  波特率

     */
    public SerialPortWrapperImpl(String commPortId, int baudRate) {

        this.commPortId = commPortId;
        this.baudRate = baudRate;


    }

    @Override
    public void close() throws Exception {
        port.close();
        //listeners.forEach(PortConnectionListener::closed);
        //    LOG.debug("Serial port {} closed", port.getPortName());
    }

    @Override
    public void open() {


        if (port != null) {
            // System.out.println("关闭串口");
            port.close();//关闭串口
            // System.out.println("关闭串口成功");
        } else {
            System.out.println("开始设置串口" + commPortId);
            //直接根据串口号获取串口
            //  portId = CommPortIdentifier.getPortIdentifier(portName);


            try {
                CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(commPortId);
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    // System.out.println(portId.getName());


                    /**
                     * open(String TheOwner, int i)：打开端口
                     * TheOwner 自定义一个端口名称，随便自定义即可
                     * i：打开的端口的超时时间，单位毫秒，超时则抛出异常：PortInUseException if in use.
                     * 如果此时串口已经被占用，则抛出异常：gnu.io.PortInUseException: Unknown Application
                     *
                     */

                    port = (SerialPort) portId.open("App", 2000);//open方法有两个参数，第一个是String，通常设置为你的应用程序的名字。第二个参数是时间，即开启端口超时的毫秒数。当端口被另外的应用程序占用时，将抛出PortInUseException异常。


                    port.setSerialPortParams(baudRate,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);

                    System.out.println("端口 " + commPortId + "打开成功");

                }
            } catch (UnsupportedCommOperationException e) {
                System.out.println("设置端口参数失败");

                //errorlog.scwj("设置串口参数"+e.getMessage());
            } catch (NoSuchPortException ne) {
                ne.printStackTrace();

                return;
            } catch (PortInUseException pe) {
                pe.printStackTrace();
                return;
            }
            /*
            try {
              //  this.port.enableReceiveTimeout(20);
                // return false;

            } catch (UnsupportedCommOperationException e)
            {
            }

             */


        }


    }


    @Override
    public InputStream getInputStream() {

        try {
            return port.getInputStream();
        } catch (Exception e) {

        }

        return null;

    }

    @Override
    public OutputStream getOutputStream() {

        try {
            return port.getOutputStream();
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public int getBaudRate() {
        return baudRate;
        //return SerialPort.BAUDRATE_9600;
    }


    @Override
    public int getDataBits() {
        return dataBits;
        //return SerialPort.DATABITS_8;
    }

    @Override
    public int getStopBits() {
        return stopBits;
        //return SerialPort.STOPBITS_1;
    }

    @Override
    public int getParity() {
        return parity;
        //return SerialPort.PARITY_NONE;
    }
}