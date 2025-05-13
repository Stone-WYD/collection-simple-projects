package com.jgdsun.ba.modbusip;


import com.jgdsun.ba.control.BaService;
import com.jgdsun.ba.control.bean.BaControlAction;
import com.jgdsun.ba.control.bean.BaControlListener;
import com.jgdsun.ba.modbusip.brand.KkqImpl;
import com.jgdsun.ba.mqtt.MQTTService;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.service.DataService;

import com.jgdsun.ba.utils.modbus.Modbus4jReadUtils;
import com.jgdsun.ba.utils.modbus.Modbus4jWriteUtils;
import com.jgdsun.ba.utils.modbus.ModbusCommandBean;
import com.jgdsun.ba.utils.modbus.TcpMaster;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModBusAction implements BaControlListener {


    private DataService dataService;



    /** ups对象处理类*/
    Map<String, ModbusServer> serverMap = new HashMap<>();



    /** modbus读处理类*/
    Map<String, Modbus4jReadUtils> modbus4jReadUtilsMap = new HashMap<>();

    /** modbus写处理类*/
    Map<String, Modbus4jWriteUtils> modbus4jWriteUtilsMap = new HashMap<>();




    /** 所有需要读取的基本命令集合*/
    private List<ModbusCommandBean> allBaseReadCommandList = new ArrayList<>();



    private static Server server = null;

    /**
     *
     * @param
     */
    public ModBusAction()
    {


        dataService = new DataService();


        try
        {
            server = new Server(8078);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            // Or ServletContextHandler.NO_SESSIONS
            context.setContextPath("/api/v1/");
            server.setHandler(context);

            // 监听门禁开门
            context.addServlet(new ServletHolder(new BaControlAction(this)), "/baControl");
            server.start();

            System.out.println("服务启动成功");

        }catch (Exception e)
        {

            e.printStackTrace();
        }


        System.out.println("加载读取数据");

        initReadInfo();



        loopRead();


        new MQTTService(new BaService());




    }







    private ModbusCommandBean replayBean = new ModbusCommandBean();




    /**初始化需要轮询的信息*/
    public void initReadInfo()
    {


        List<String> ipList = dataService.getAllIp();

        if(ipList!=null&&!ipList.isEmpty())
        {
            for(String lightIp:ipList) {


                ModbusMaster tcpMaster = TcpMaster.getMaster(lightIp,502);


                Modbus4jReadUtils rtuModbus4jReadUtils = new Modbus4jReadUtils(tcpMaster);

                Modbus4jWriteUtils rtuModbus4jWriteUtils = new Modbus4jWriteUtils(tcpMaster);

                modbus4jReadUtilsMap.put(lightIp,rtuModbus4jReadUtils);

                modbus4jWriteUtilsMap.put(lightIp,rtuModbus4jWriteUtils);



                List<String> slaveIdList = dataService.getAllParamAddressByIp(lightIp);
                if(slaveIdList!=null && !slaveIdList.isEmpty())
                {
                    for(String lightSlave:slaveIdList)
                    {
                        ModbusServer lightServer = new KkqImpl(lightIp,Integer.parseInt(lightSlave));

                        serverMap.put(lightIp+"_"+lightSlave,lightServer);


                        allBaseReadCommandList.addAll(lightServer.getAllReadCommand());

                    }


                }
            }
        }




    }





    /**如果通讯异常 重复读取状态的间隔*/
    private long sendErrorJg = 5000;
    /**通讯正常 重复读取 基础信息的间隔*/
    private long readBaseInfoJg = 10000;

    /**是否有异常*/
    private boolean isError = false;


    /**失败次数*/
    private int errorTimes = 0;

    /**读取休眠频率*/
    private int sleep = 100;


    private int readBaseNowCount = 0;

    private int readBaseJg = 3*1000/sleep;


    private void loopRead()
    {
        Thread t = new Thread()
        {
            public void run()
            {

                while(!isDestory) {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //需写入的内容
                    if(writeList.size()>0)
                    {
                        for(ModbusCommandBean bean:writeList)
                        {
                            writeList.remove(bean);

                            sendCommand(bean);
                        }
                    }

                    //需快速读取的内容
                    if(fastReadList.size()>0)
                    {
                        for(ModbusCommandBean bean:fastReadList)
                        {
                            fastReadList.remove(bean);

                            sendCommand(bean);
                        }
                    }


                    //读取基本信息
                    readBaseNowCount++;
                    if(readBaseNowCount>readBaseJg)
                    {
                        readBaseNowCount = 0;

                        for(ModbusCommandBean bean:allBaseReadCommandList)
                        {
                            sendCommand(bean);

                            try {
                                sleep(20);
                            }catch(InterruptedException e)
                            {

                            }
                        }




                    }

                }

            }
        };

        t.start();
    }


    private void sendCommand(ModbusCommandBean bean)
    {







        if(bean.getGnType()==1)
        {
            try {
                if(bean.getFunction()==3 || bean.getFunction() == 4) {


                    System.out.println("准备发送功能码3数据 "+bean.getSlaveId()+"  getAddress() "+bean.getAddress());



                    short[] list = null;

                    if(bean.getFunction() == 3) {

                        list = modbus4jReadUtilsMap.get(bean.getIp()).readHoldingRegister(bean.getSlaveId(), bean.getAddress(), bean.getNumberOfBits());

                    }else if(bean.getFunction() == 4)
                    {
                        list = modbus4jReadUtilsMap.get(bean.getIp()).readInputRegisters(bean.getSlaveId(), bean.getAddress(), bean.getNumberOfBits());
                    }



                    if(serverMap.get(bean.getIp()+"_"+bean.getSlaveId())!=null)
                    {
                        serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).readCommandBack(true,bean.getSlaveId(),bean.getAddress(),list,bean.getNumberOfBits());
                    }








                }else if(bean.getFunction()==5)
                {
                    boolean[] list = modbus4jReadUtilsMap.get(bean.getIp()).readCoilStatus(bean.getSlaveId(), bean.getAddress(), bean.getNumberOfBits());

                }else if(bean.getFunction()==2)//读取继电器RTU输入状态
                {

                }


                // rtuModbus4jWriteUtils.writeHoldingRegister(1,2,34, DataType.TWO_BYTE_INT_SIGNED);
            } catch (ModbusTransportException e) {

                e.printStackTrace();
                //e.printStackTrace();
                //        MyApplication.doDebug("","异常1 "+e.getMessage());



                if(bean.getEquipmentType() == 1)//ups
                {
                    if(serverMap.get(bean.getIp()+"_"+bean.getSlaveId())!=null)
                    {
                        serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).readCommandBack(false,bean.getSlaveId(),bean.getAddress(),null,bean.getNumberOfBits());
                    }

                }






                errorTimes++;

                System.out.println("异常errorTimes "+errorTimes);

                if(errorTimes>5)
                {

                    try {



                        errorTimes = 0;

                    }catch(Exception ea)
                    {

                    }

                }

                //    isError = true;


                System.out.println("异常1  从站 "+bean.getSlaveId());

            } catch (ErrorResponseException e) {

                //     MyApplication.doDebug("","异常2 "+e.getMessage());

                isError = true;

                e.printStackTrace();
                System.out.println("异常2  从站 "+bean.getSlaveId());


                if(serverMap.get(bean.getIp()+"_"+bean.getSlaveId())!=null)
                {
                    serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).readCommandBack(false,bean.getSlaveId(),bean.getAddress(),null,bean.getNumberOfBits());
                }



            } catch (ModbusInitException e) {

                //      MyApplication.doDebug("","异常3 "+e.getMessage());


                isError = true;

                e.printStackTrace();
                System.out.println("异常3  从站 "+bean.getSlaveId());



                if(serverMap.get(bean.getIp()+"_"+bean.getSlaveId())!=null)
                {
                    serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).readCommandBack(false,bean.getSlaveId(),bean.getAddress(),null,bean.getNumberOfBits());
                }



            }catch(Exception e)
            {


                isError = true;


                e.printStackTrace();
                System.out.println("异常4  从站 "+bean.getSlaveId());




                if(serverMap.get(bean.getIp()+"_"+bean.getSlaveId())!=null)
                {
                    serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).readCommandBack(false,bean.getSlaveId(),bean.getAddress(),null,bean.getNumberOfBits());
                }



            }

        }else if(bean.getGnType()==2)//写
        {
            try {
                if(bean.getFunction() == 3)
                {
                    System.out.println("发送写入命令3");

                    if(bean.getDataValue().length == 1)
                    {
                        modbus4jWriteUtilsMap.get(bean.getIp()).writeHoldingRegister(bean.getSlaveId(),bean.getAddress(),bean.getDataValue()[0],DataType.TWO_BYTE_INT_SIGNED);
                    }else {

                        modbus4jWriteUtilsMap.get(bean.getIp()).writeRegisters(bean.getSlaveId(),bean.getAddress(),bean.getDataValue());
                    }




                }else if(bean.getFunction() == 5)
                {
                    modbus4jWriteUtilsMap.get(bean.getIp()).writeCoil(bean.getSlaveId(),bean.getAddress(),bean.isCoilsValue());

                }


                if(serverMap.get(bean.getIp()+"_"+bean.getSlaveId())!=null)
                {
                    //写成功后快速读取寄存器内容
                    fastReadList.add(serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).getReadCommandOne(bean.getAddress(),bean.getDataValue().length));
                }




            } catch (ModbusTransportException e) {
                e.printStackTrace();

                if (bean.getSlaveId() <10)
                {
                    isError = true;
                }

                System.out.println("异常1写  从站 "+bean.getSlaveId());







            } catch (ModbusInitException e) {

                if (bean.getSlaveId() <10)
                {
                    isError = true;
                }
                e.printStackTrace();
                System.out.println("异常3写  从站 "+bean.getSlaveId());






            }catch(Exception e)
            {

                if (bean.getSlaveId() <10)
                {
                    isError = true;
                }
                System.out.println("异常4写  从站 "+bean.getSlaveId());



            }
        }


    }




    public boolean isDestory = false;
    /**
     * 销毁
     */
    public void onDestory()
    {
        isDestory = true;
        System.out.println("销毁 modbus");
        try {

            // rtuMaster.destroy();
        }catch (Exception e)
        {

        }
    }

    /**
     * 需写入的命令
     */


    List<ModbusCommandBean> writeList = new CopyOnWriteArrayList();


    /*
    @Override
    public void lightControl(LightControlBean bean) {


        String id = bean.getId();

        String ip = bean.getIp();



        if(serverMap.get(ip+"_"+bean.getAddress())!=null)
        {
            writeList.add(serverMap.get(ip+"_"+bean.getAddress()).getWriteCommand(bean.getRegisterAddress(),bean.getStatus()));
        }

    }

     */

    /**
     * 需快速读取的命令
     */
    List<ModbusCommandBean> fastReadList = new CopyOnWriteArrayList();



    @Override
    public void baWirte(String parameterId, String value) {
       // baService.baWirte(parameterId, value);
        System.out.println("ba控制 parameterId "+parameterId+" value "+value);

        TBaParameter tBaParameter = dataService.getAllParameterById(parameterId);

        if(tBaParameter != null)
        {
            String ip = tBaParameter.getIp();
            String slave = tBaParameter.getDevid();
            int index = tBaParameter.getBaIndex();

            if(serverMap.get(ip+"_"+slave)!=null)
            {
                ModbusCommandBean setCommandBean = serverMap.get(ip+"_"+slave).getWriteCommand(index,Integer.parseInt(value));

                if(setCommandBean != null) {
                    writeList.add(setCommandBean);
                }

                //写成功后快速读取寄存器内容
               // fastReadList.add(serverMap.get(bean.getIp()+"_"+bean.getSlaveId()).getReadCommandOne(bean.getAddress(),bean.getDataValue().length));
            }

        }

    }


}
