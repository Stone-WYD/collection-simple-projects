package com.jgdsun.ba.utils.modbus;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;

public class TcpMaster {
	 
    private static ModbusFactory modbusFactory;
 
    static {
        if (modbusFactory == null) {
            modbusFactory = new ModbusFactory();
        }
    }
 
    /**
     * 获取master
     *
     * @return master
     */
    public static ModbusMaster getMaster(String ip,int port) {
        IpParameters params = new IpParameters();
        
        


        
        
        params.setHost(ip);
        params.setPort(port);

        //这个属性确定了协议帧是否是通过tcp封装的RTU结构，采用modbus tcp/ip时，要设为false, 采用modbus rtu over tcp/ip时，要设为true
        params.setEncapsulated(false);


        ModbusMaster master = modbusFactory.createTcpMaster(params, false);// TCP 协议
        try {
            //设置超时时间
            master.setTimeout(1000);
            //设置重连次数
            master.setRetries(2);

            //初始化
            master.init();
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
        return master;
    }
}
