package com.jgdsun.ba.utils.modbus;


import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.serial.SerialPortWrapper;


public class RTUMaster {
	 
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
    public static ModbusMaster getMaster(SerialPortWrapper serialParameters) {
       
        
        
       // PropertiesUtil propertiesUtil = new PropertiesUtil("config.properties");
        
       // String comm = "/dev/ttyCOM2";

        //String 	comm = "/dev/ttyCOM2";
       // int baudRate = 9600;
        
        //String _comm = propertiesUtil.readProperty("comm");
       // if(_comm!=null&&!_comm.equals(""))
        //{
       // 	comm = _comm;
       // }
       // String _baudRate = propertiesUtil.readProperty("baudRate");
        
       // try {
       // 	baudRate = Integer.parseInt(_baudRate);
		//} catch (NumberFormatException e) {
			// TODO: handle exception
		//}
        
        ////SerialPortWrapper serialParameters = new
          //      SerialPortWrapperImpl(comm, baudRate);
        /* 创建ModbusFactory工厂实例 */
        ModbusFactory modbusFactory = new ModbusFactory();
        /* 创建ModbusMaster实例 */




        ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);




       // params.setEncapsulated(true);
      
        try {
          
            //初始化
            master.init();
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
        return master;
    }
}
