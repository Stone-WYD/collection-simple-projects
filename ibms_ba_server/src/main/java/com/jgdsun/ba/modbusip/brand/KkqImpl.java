package com.jgdsun.ba.modbusip.brand;

import com.google.gson.Gson;
import com.jgdsun.ba.modbusip.ModbusServer;
import com.jgdsun.ba.mqtt.MQTTService;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.service.DataService;
import com.jgdsun.ba.utils.HexUtils;
import com.jgdsun.ba.utils.Tools;
import com.jgdsun.ba.utils.modbus.ModbusCommandBean;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 王杉
 * @description 科控奇BA
 * @createDate 2024-07-12 16:29:18
 */
public class KkqImpl implements ModbusServer {



    private DataService dataService;



    private String ip;

    private int slaveId;


    private int begIndex = 0;

    //modbus 最大一帧读取 读125  写 123个寄存器，超过此值 需分段读取
    private int len = 0;

    //单帧寄存器个数使用 123个
    private int oneLen = 123;


    Map<Integer, TBaParameter> lightMap = new HashMap<>();
    List<TBaParameter> lightList;


    /**
     * 存储最后数据
     */
    Map<Integer, String> lastValueMap = new HashMap<>();


    private Gson gson;


    public KkqImpl(String ip, int slaveId)
    {

        gson = new Gson();

        dataService = new DataService();

        this.ip = ip;
        this.slaveId = slaveId;

        lightList = dataService.getAllLightByIpAndSlaveId(ip,""+slaveId);

        if(lightList!=null && !lightList.isEmpty()) {

            for (TBaParameter light : lightList) {
                lightMap.put(light.getBaIndex(), light);

                lastValueMap.put(light.getBaIndex(), "");
            }

            begIndex = lightList.get(0).getBaIndex();

            // len = lightList.get(lightList.size()-1).getBaIndex() - begIndex +1 + lightList.get(lightList.size()-1).getDataLen() -1;

            len = lightList.get(lightList.size()-1).getBaIndex() - begIndex  + lightList.get(lightList.size()-1).getDataLen() ;
        }



    }


    /**
     * 获取所有需要读取的command
     * @return
     */
    public List<ModbusCommandBean> getAllReadCommand()
    {

        //this.equipmentId = equipmentId;
        //this.slaveId = slaveId;

        System.out.println("len "+len);


        List<ModbusCommandBean> commandList = new ArrayList<>();




        if(lightList!=null && !lightList.isEmpty()) {

            for (TBaParameter light : lightList) {


                ModbusCommandBean baseInfoCommandBean = new ModbusCommandBean();
                baseInfoCommandBean.setEquipmentType(1);
                baseInfoCommandBean.setIp(ip);

                baseInfoCommandBean.setGnType(1);
                baseInfoCommandBean.setSlaveId(slaveId);
                baseInfoCommandBean.setAddress(light.getBaIndex());
                baseInfoCommandBean.setFunction(3);
                baseInfoCommandBean.setNumberOfBits(light.getDataLen());

                commandList.add(baseInfoCommandBean);

            }


        }

        /*


        if(len<=oneLen) {

            //基本信息
            ModbusCommandBean baseInfoCommandBean = new ModbusCommandBean();
            baseInfoCommandBean.setEquipmentType(1);
            baseInfoCommandBean.setIp(ip);

            baseInfoCommandBean.setGnType(1);
            baseInfoCommandBean.setSlaveId(slaveId);
            baseInfoCommandBean.setAddress(begIndex);
            baseInfoCommandBean.setFunction(3);
            baseInfoCommandBean.setNumberOfBits(len);

            commandList.add(baseInfoCommandBean);
        }else
        {
            int k = (len+(oneLen - 1))/oneLen;

            for(int i = 0;i<k;i++)
            {
                ModbusCommandBean baseInfoCommandBean = new ModbusCommandBean();
                baseInfoCommandBean.setEquipmentType(1);
                baseInfoCommandBean.setIp(ip);

                baseInfoCommandBean.setGnType(1);
                baseInfoCommandBean.setSlaveId(slaveId);
                baseInfoCommandBean.setAddress(begIndex+(i*oneLen));
                baseInfoCommandBean.setFunction(3);
                baseInfoCommandBean.setNumberOfBits(Math.min(len-begIndex+(i*oneLen),oneLen));

                commandList.add(baseInfoCommandBean);
            }
        }

         */

        // System.out.println("")


        return commandList;


    }


    /**
     * 读取命令返回
     * @param slaveId 站号
     * @param address 开启寄存器地址
     * @param data 数据
     */
    @Override
    public void readCommandBack(boolean flag,int slaveId,int address,short data[],int readLen)
    {


        System.out.println("基本信息返回 ");

        if(flag) {

            checkBaseInfo(address,data);
        }else
        {

            checkBaseInfoError(address,readLen);
        }



    }



    private ArrayList<TBaParameter> updateList = new ArrayList<>();
    /**
     * 处理灯的返回信息
     * @param data
     */
    private void checkBaseInfo(int address,short data[])
    {

        errorCount = 0;

        updateList.clear();

        for(int i=0;i<data.length;i++)
        {
            if((lightMap.get(address+i))!=null)
            {


                int _len = lightMap.get(address+i).getDataLen();

                String str = "";

                for(int j=0;j<_len;j++)
                {
                    str = str+data[i + j];
                }


                if(lastValueMap.get(address+i).equals(str))
                {
                    continue;
                }

                lastValueMap.put(address+i,str);

                Integer dataType = lightMap.get(address+i).getDataType();


                String value = ""+data[i];

                if(dataType != null)
                {

                    int ivalue = data[i];

                    int dataLen = lightMap.get(address+i).getDataLen();


                    int dataCoefficient = lightMap.get(address+i).getDataCoefficient();

                    int digit = lightMap.get(address+i).getDigit();


                    if(dataType == 0)
                    {

                        if(dataLen == 2)
                        {
                            ivalue = data[i]*256 + data[i+1];
                        }

                        if(digit == 0)
                        {
                            value = ""+Tools.getFloatUpToInteger(ivalue*1.0f/dataCoefficient);
                        }else {

                            value = ""+Tools.getFloatUp(ivalue*1.0f/dataCoefficient,digit);

                        }




                    }else if(dataType == 1)
                    {



                        if(dataLen == 2)
                        {
                            ivalue = data[i]*256 + data[i+1];
                        }

                        ivalue = Tools.getFloatUpToInteger(ivalue*1.0f/dataCoefficient);


                        String svalue = Integer.toBinaryString(ivalue);

                        value = ""+(svalue.length() -1);

                    }else if(dataType == 20)//咸宁空调模式 使用 1,4,8,16 分别对应  制冷，风扇，制热，自动
                    {


                        String dataMaping = lightMap.get(address+i).getDataMaping();

                        boolean isHave = false;

                        if(!StringUtils.isEmpty(dataMaping))
                        {

                            String maping[] = dataMaping.split("\\|");


                            for(int _i = 0; _i < maping.length; _i++)
                            {

                                if(value.equals(maping[_i]))
                                {
                                    isHave = true;

                                    value = ""+_i;
                                    break;
                                }

                            }

                        }

                        if(!isHave)value="0";

                    }



                }




                       Map map = new HashMap();

                       map.put("id",lightMap.get(address+i).getId());
                       map.put("value",value);

                        MQTTService.publish(statuschangeTopic,gson.toJson(map));



                lightMap.get(address+i).setLastvalue(value);

                updateList.add(lightMap.get(address+i));



            }
        }

        //更新数据库状态
        if(!updateList.isEmpty())
        {
            dataService.updateLightInfo(updateList);
        }


    }

    /**失败次数*/
    int errorCount = 0;

    /**
     * 处理灯的异常返回
     */
    private void checkBaseInfoError(int address,int readLen)
    {

        errorCount++;

        if(errorCount>2) {

            errorCount = 0;

            updateList.clear();






            /*

            Set<Integer> lightKey = lightMap.keySet();

            for (int key:lightKey) {



                        if (!lightMap.get(key).getStatus().equals("0")) {

                            LightStateChangeBean lightStateChangeBean = new LightStateChangeBean();
                            lightStateChangeBean.setId(lightMap.get(key).getId());
                            lightStateChangeBean.setStoreyId(lightMap.get(key).getStoreyId());
                            lightStateChangeBean.setNowstatus("0");

                            MQTTService.publish(statuschangeTopic, gson.toJson(lightStateChangeBean));

                            lightMap.get(key).setStatus("0");

                            updateList.add(lightMap.get(key));


                        }



            }

             */

            //更新数据库状态
            //if (!updateList.isEmpty()) {
            //  dataService.updateLightInfo(updateList);
            // }
        }


    }

    @Override
    public ModbusCommandBean getWriteCommand(int registerAddress, int value) {

        short data[] = new short[1];
        data[0] = (short)value;

        if(lightMap.get(registerAddress) != null)
        {



            Integer dataType = lightMap.get(registerAddress).getDataType();
            int dataLen = lightMap.get(registerAddress).getDataLen();


            int dataCoefficient = lightMap.get(registerAddress).getDataCoefficient();

            int digit = lightMap.get(registerAddress).getDigit();

            System.out.println("dataType "+dataType);


            if(dataType != null)
            {
                if(dataType == 0)
                {
                    // data[0] = (short)(value*dataCoefficient);
                    data[0] = (short)(value);//写时未使用系数
                }else if(dataType == 1)
                {

                    String str = "0";

                    if(value > 0)
                    {

                        for(int i=0;i<value-1;i++)
                        {

                            str = "0"+str;
                        }

                        str = "1"+str;
                    }

                    int _v = Integer.parseInt(str,2);

                    System.out.println("设置数据 "+_v);

                    //data[0] = (short)(_v*dataCoefficient);
                    data[0] = (short)(_v);//写时未使用系数

                }else if(dataType == 20)//咸宁空调模式 使用 1,4,8,16 分别对应  制冷，风扇，制热，自动
                {


                    String dataMaping = lightMap.get(registerAddress).getDataMaping();


                    if(!StringUtils.isEmpty(dataMaping))
                    {

                        String maping[] = dataMaping.split("\\|");


                        if(value<maping.length)
                        {

                            data[0] =  Short.parseShort(maping[value]);

                        }

                    }

                }
            }

            System.out.println("data[i]" +data[0]);

            //基本信息
            ModbusCommandBean setCommandBean = new ModbusCommandBean();
            setCommandBean.setEquipmentType(1);
            setCommandBean.setIp(ip);

            setCommandBean.setGnType(2);
            setCommandBean.setSlaveId(slaveId);
            setCommandBean.setAddress(registerAddress);
            setCommandBean.setFunction(3);
            setCommandBean.setDataValue(data);
            setCommandBean.setNumberOfBits(1);

            return setCommandBean;

        }

        return null;












    }

    @Override
    public ModbusCommandBean getReadCommandOne(int registerAddress,int len) {





        //基本信息
        ModbusCommandBean baseInfoCommandBean = new ModbusCommandBean();
        baseInfoCommandBean.setEquipmentType(1);
        baseInfoCommandBean.setIp(ip);

        baseInfoCommandBean.setGnType(1);
        baseInfoCommandBean.setSlaveId(slaveId);
        baseInfoCommandBean.setAddress(registerAddress);
        baseInfoCommandBean.setFunction(3);
        baseInfoCommandBean.setNumberOfBits(len);

        return baseInfoCommandBean;
    }



}
