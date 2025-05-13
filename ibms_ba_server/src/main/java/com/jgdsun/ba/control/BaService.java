package com.jgdsun.ba.control;

import com.google.gson.Gson;
import com.jgdsun.ba.mqtt.MQTTListener;
import com.jgdsun.ba.mqtt.MQTTService;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.entity.TBaParameterDuration;
import com.jgdsun.ba.service.DataService;
import com.jgdsun.ba.utils.Tools;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetTimeoutException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.enumerated.BinaryPV;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.Boolean;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.util.BACnetUtils;
import com.serotonin.bacnet4j.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * @Description: BA设备信息读取
 * @Author: 王杉
 * @Date: 2022/9/3.
 */
@Slf4j
public class BaService implements MQTTListener {



    private static LocalDevice d;

    private static Map<Integer, RemoteDevice> remoteDeviceMap = new HashMap<>();

    /**
     * 需写入队列
     */
    public static ConcurrentLinkedQueue<TBaParameter> writeQueue = new ConcurrentLinkedQueue<TBaParameter>();

    private DataService dataService;

    private String localIp = "10.1.9.100";
    // private String localIp = "10.1.9.250";

    List<TBaParameter> paramterList;

    HashMap<String, List> paramterMap = new HashMap();

    HashMap<String, List> paramterDevMap = new HashMap();

    /**BA变更推送订阅*/
    private static String baValueChangeTopic = "ba/change";

    /**
     * ip dev ip与设备对应
     */
    HashMap<String, String> ipDevMap = new HashMap();


    private Gson gson;


    public BaService() {
        gson = new Gson();
        dataService = new DataService();

        paramterList = dataService.getAllParameters();

        for (TBaParameter parameter : paramterList) {
            String ip = parameter.getIp();
            String deviceId = parameter.getDevid();

            List<TBaParameter> list = paramterMap.get(ip + "_" + deviceId);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(parameter);

            paramterMap.put(ip + "_" + deviceId, list);

            //--dev 对应

            List<TBaParameter> list2 = paramterDevMap.get(deviceId);
            if (list2 == null) {
                list2 = new ArrayList<>();
            }

            list2.add(parameter);

            ipDevMap.put(parameter.getIp() + "_" + deviceId, deviceId);

            paramterDevMap.put(deviceId, list2);

        }
        System.out.println("---------------");
        try {
            getLocalDevice();
        } catch (Exception e) {
            log.error("init local device fail, reason:{}", e.getMessage());
        }
    }

    /**
     * 读取BA数据
     */
    public void readOne1() {
        //if(paramterList == null || paramterList.isEmpty())return;


        Set ips = ipDevMap.keySet();

        Iterator iterator = ips.iterator();

        while (iterator.hasNext()) {
            String ip = "" + iterator.next();


            LocalDevice device = null;

            try {
                device = localDeviceConnect(ip);
                //获取远程连接设备

                device.initialize();

                device.startRemoteDeviceDiscovery();

                RemoteDevice rd = device.getRemoteDeviceBlocking(Integer.parseInt(ipDevMap.get(ip)));

                if (rd != null) {


                    List<TBaParameter> list = paramterDevMap.get(ipDevMap.get(ip));

                    if (list != null && !list.isEmpty()) {


                        for (TBaParameter parameter : list) {
                            if (parameter.getType().equals("BV")) {
                                System.out.println("binaryValue name = " + RequestUtils.readProperty(device, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));

                                // System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.presentValue, null));
                            } else {
                                System.out.println("binaryValue name = " + RequestUtils.readProperty(device, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));

                            }

                        }
                    }


                }

                /*



                 */


            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (device != null) {
                    device.terminate();
                }
            }


        }


    }


    /**
     * 读取BA数据
     *
     * @param bvSave
     */
    public void readOne(boolean bvSave) {
        if (paramterList == null || paramterList.isEmpty()) return;


        long beg = new Date().getTime();

        Set ips = ipDevMap.keySet();

        Iterator ipiterator = ips.iterator();

        while (ipiterator.hasNext()) {
            String ip_dev = "" + ipiterator.next();
            int index = ip_dev.indexOf("_");
            String ip = ip_dev.substring(0, index);
            System.out.println("ip " + ip);
            try {

                String dev = ipDevMap.get(ip_dev);

                System.out.println("开始扫描 " + dev);

//                RemoteDevice rd = d.getRemoteDeviceBlocking(Integer.parseInt(dev));//获取远程设备，instanceNumber 是设备的device id
                RemoteDevice rd = getRemoteDevice(dev);

                List<TBaParameter> list = paramterDevMap.get(dev);
                System.out.println("rd " + rd);

                if (list != null && !list.isEmpty()) {
                    for (TBaParameter parameter : list) {

                        try {
                            System.out.println(parameter.getIp() + "  " + parameter.getDevid() + "  " + parameter.getBaIndex());

                            if (parameter.getType().equals("BV")) {
                                //System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));
                                String value = "";

                                if (StringUtils.isEmpty(parameter.getIoType())) {
                                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                                } else if (parameter.getIoType().equals("BI")) {
                                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryInput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);

                                } else if (parameter.getIoType().equals("BO")) {
                                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryOutput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);

                                }

                                System.out.println("=-------------------bValue=" + value);

                                String nv = "0";
                                if (value.equals("inactive"))//
                                {

                                } else if (value.equals("active")) {
                                    nv = "1";
                                }
                                if (!parameter.getLastvalue().equals(nv)) {
                                    //先执行检测
                                    checkDuration(parameter, nv);
                                    parameter.setLastvalue(nv);
                                    parameter.setLasttime(new Date());
                                    dataService.saveBAvalue(parameter);
                                }
                            } else if (parameter.getType().equals("AV")) {
                                if (bvSave) {
                                    // System.out.println("a name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));
                                    String value = "";

                                    if (StringUtils.isEmpty(parameter.getIoType())) {
                                        value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                                    } else if (parameter.getIoType().equals("AI")) {
                                        value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogInput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                                    } else if (parameter.getIoType().equals("AO")) {
                                        value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogOutput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);

                                    }
                                    System.out.println("=-------------------aValue=" + value);
                                    int digit = parameter.getDigit();
                                    String v2 = "";
                                    if (digit == 0) {
                                        v2 = "" + Tools.getFloatUpToInteger(Float.parseFloat(value));
                                    } else {
                                        float v = Tools.getFloatUp(Float.parseFloat(value), digit);
                                        v2 = "" + v;
                                    }
                                    if (!parameter.getLastvalue().equals(v2)) {
                                        parameter.setLastvalue(v2);
                                        parameter.setLasttime(new Date());
                                        dataService.saveBAvalue(parameter);
                                    }
                                }
                            } else if (parameter.getType().equals("BI")) {
                                System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryInput, parameter.getBaIndex()), PropertyIdentifier.objectName, null));
                                String value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryInput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                                System.out.println("=-------------------bValue=" + value);
                                String nv = "0";
                                if (value.equals("active")) {
                                    nv = "1";
                                }

                                if (!parameter.getLastvalue().equals(nv)) {
                                    //先执行检测
                                    checkDuration(parameter, nv);
                                    parameter.setLastvalue(nv);
                                    parameter.setLasttime(new Date());
                                    dataService.saveBAvalue(parameter);
                                }
                            }

                        } catch (Exception e) {
                            log.error("read ba value failed in for List, reason:{},failed Device:{}", e.getMessage(),parameter.toString());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("read ba value failed, reason:{}", e.getMessage());
            }
            // 取消关闭
//            finally {
//                if (d != null) {
//                    System.out.println("关闭bacnet");
//                    // d.terminate();
//                    System.out.println("关闭bacnet 成功");
//                }
//            }
        }
        System.out.println("用时-------------- " + (new Date().getTime() - beg));
    }

//      旧用方式，弃用
//    /**
//     * 更改BA数据
//     *
//     * @param id
//     * @param value
//     */
//    public void writeOne(String id, String value) {
//
//        TBaParameter parameter = dataService.getAllParameterById(id);
//        if (parameter == null) {
//            return;
//        }
//        try {
//            System.out.println("开始扫描 " + parameter.getDevid());
//            RemoteDevice rd = getRemoteDevice(parameter.getDevid());
//
//            //必须先修改out of service为true
//            // RequestUtils.writeProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()),PropertyIdentifier.outOfService, 1);
//            // Thread.sleep(1000);
//            //修改属性值
//            if (parameter.getType().equals("BV")) {
//                //   RequestUtils.writeProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()),PropertyIdentifier.outOfService,  Boolean.TRUE);
//
//                if (value.equals("1")) {
//                    RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), Boolean.TRUE);
//                } else {
//                    RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), Boolean.FALSE);
//                }
//            } else if (parameter.getType().equals("AV")) {
//
//                //  RequestUtils.writeProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()),PropertyIdentifier.outOfService,  Boolean.TRUE);
//
//                RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), new Real(Float.parseFloat(value)));
//            }
//
//
//            //  Thread.sleep(2000);
//            // System.out.println("analogValue0= " +RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 0), PropertyIdentifier.presentValue, null));
//
//
//            //  Thread.sleep(1000);
//            //修改属性值
//            //    RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.analogValue, 0), new Real(77));
//            // Thread.sleep(2000);
//            //  System.out.println("analogValue0= " +RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 0), PropertyIdentifier.presentValue, null));
//
//
//        } catch (Exception e) {
//
//            log.error("write ba value failed, reason:{}", e.getMessage());
//
//        } finally {
//            if (d != null) {
//                System.out.println("关闭bacnet");
//                d.terminate();
//                System.out.println("关闭bacnet 成功");
//            }
//
//        }
//
//
//    }


    /**
     * 更改BA数据
     *
     * @param value
     */
    public void writeOne(TBaParameter parameter, String value) {
        parameter.setLastvalue(value);

        System.out.println("放入待写区--------");
        writeQueue.offer(parameter);
        System.out.println("放入待写区2--------" + writeQueue);
    }

    /**
     * 检测是否需要写入
     */
    public void baWirte(String parameterId, String value) {
        TBaParameter parameter = dataService.getAllParameterById(parameterId);
        writeOne(parameter, value);
    }

    /**
     * 检测是否需要写入
     */
    public void checkWrite() {
        //  System.out.println("检测是否有写入1 ");
        for (; ; ) {
            TBaParameter parameter = writeQueue.poll();
            if (parameter != null) {

                System.out.println("检测是否有写入 " + parameter);
                writeOne2(parameter, parameter.getLastvalue());
            } else {
                break;
            }
        }
    }


    /**
     * 更改BA数据
     *
     * @param value
     */
    public static void writeOne2(TBaParameter parameter, String value) {


        if (parameter == null) return;


        RemoteDevice rd = null;
        try {
            //创建网络对象
            System.out.println("开始扫描 " + parameter.getDevid());
            try {
                //   rd = d.getRemoteDeviceBlocking(Integer.parseInt(parameter.getDevid()));//获取远程设备，instanceNumber 是设备的device id
                rd = getRemoteDevice(parameter.getDevid());
            } catch (BACnetTimeoutException timeout) {
                System.out.println("---重新获取-----------------");
                rd = getRemoteDevice(parameter.getDevid());

//                //ws d.terminate();
//                d = getLocalDevice();
//                // rd = d.getRemoteDeviceBlocking(Integer.parseInt(parameter.getDevid()));//获取远程设备，instanceNumber 是设备的device id
//                rd = remoteDeviceMap.get(Integer.parseInt(parameter.getDevid())); //d.getRemoteDeviceBlocking(Integer.parseInt(dev));//获取远程设备，instanceNumber 是设备的device id
//
//                if (rd == null) {
//                    rd = d.getRemoteDeviceBlocking(Integer.parseInt(parameter.getDevid()));//获取远程设备
//
//                    if (rd != null) {
//                        remoteDeviceMap.put(Integer.parseInt(parameter.getDevid()), rd);
//                    }
//                }
                System.out.println("-----------------重新获取成功------");
            }
            //修改属性值
            if (parameter.getType().equals("BV")) {
                if (value.equals("1")) {
                    RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), BinaryPV.active);
                    System.out.println("写入完成");
                } else {
                    RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), BinaryPV.inactive);
                    System.out.println("写入完成");
                }
            } else if (parameter.getType().equals("AV")) {
                RequestUtils.writePresentValue(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), new Real(Float.parseFloat(value)));
            }
            System.out.println("写入结束----");
            //修改完需回读
            Thread.sleep(100);
            readOne2(d, rd, parameter);

        } catch (Exception e) {
            log.error("write2 ba value failed, reason:{}", e.getMessage());
        }
//        finally {
//            if (d != null) {
//                System.out.println("关闭bacnet");
//                //ws  d.terminate();
//                System.out.println("关闭bacnet 成功");
//            }
//        }


    }


    public static void readOne2(LocalDevice d, RemoteDevice rd, TBaParameter parameter) {

        DataService dataService = new DataService();
        try {
            String ip = parameter.getIp();
            String dev = parameter.getDevid();
            System.out.println("readOne2 开始扫描 " + parameter.getDevid());
            System.out.println("ip " + ip);
            System.out.println("开始扫描 " + dev);
            System.out.println(parameter.getIp() + "  " + parameter.getDevid() + "  " + parameter.getBaIndex());

            if (parameter.getType().equals("BV")) {
                //System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));
                String value = "";

                if (StringUtils.isEmpty(parameter.getIoType())) {
                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                } else if (parameter.getIoType().equals("BI")) {
                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryInput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                } else if (parameter.getIoType().equals("BO")) {
                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryOutput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                }
                System.out.println("=-------------------bValue=" + value);

                String nv = "0";
                if (value.equals("active")) {
                    nv = "1";
                }
                if (!nv.equals(parameter.getLastvalue())) {
                    //先执行检测
                    parameter.setLastvalue(nv);
                }
                parameter.setLasttime(new Date());
                dataService.saveBAvalue(parameter);

                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("id", parameter.getEquipmentId());
                MQTTService.publish(baValueChangeTopic,new Gson().toJson(paramMap));

                //  }
            } else if (parameter.getType().equals("AV")) {
                //if (bvSave) {
                // System.out.println("a name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));
                String value = "";

                if (StringUtils.isEmpty(parameter.getIoType())) {
                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                } else if (parameter.getIoType().equals("AI")) {
                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogInput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                } else if (parameter.getIoType().equals("AO")) {
                    value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogOutput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                }

                System.out.println("=-------------------aValue=" + value);

                int digit = parameter.getDigit() == null ? 0 : parameter.getDigit();
                String v2 = "";
                if (digit == 0) {
                    v2 = "" + Tools.getFloatUpToInteger(Float.parseFloat(value));
                } else {
                    float v = Tools.getFloatUp(Float.parseFloat(value), digit);
                    v2 = "" + v;
                }

                if (!v2.equals(parameter.getLastvalue())) {
                    parameter.setLastvalue(v2);
                }

                parameter.setLasttime(new Date());
                dataService.saveBAvalue(parameter);
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("id", parameter.getEquipmentId());
                MQTTService.publish(baValueChangeTopic,new Gson().toJson(paramMap));

            } else if (parameter.getType().equals("BI")) {
                System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryInput, parameter.getBaIndex()), PropertyIdentifier.objectName, null));
                String value = "" + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryInput, parameter.getBaIndex()), PropertyIdentifier.presentValue, null);
                System.out.println("=-------------------bValue=" + value);
                String nv = "0";
                if (value.equals("active")) {
                    nv = "1";
                }
                if (!parameter.getLastvalue().equals(nv)) {
                    parameter.setLastvalue(nv);
                }

                parameter.setLasttime(new Date());
                dataService.saveBAvalue(parameter);
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("id", parameter.getEquipmentId());
                MQTTService.publish(baValueChangeTopic,new Gson().toJson(paramMap));
            }

        } catch (Exception e) {
            log.error("read2 ba value failed, reason:{}", e.getMessage());
        }
//        finally {
//            if (d != null) {
//                System.out.println("关闭bacnet");
//                //ws d.terminate();
//                System.out.println("关闭bacnet 成功");
//            }
//        }
    }


    /**
     * 读取BA数据
     */
    public void readOne3() {
        //if(paramterList == null || paramterList.isEmpty())return;


        Set ips = ipDevMap.keySet();

        Iterator Ipiterator = ips.iterator();

        while (Ipiterator.hasNext()) {
            String ip = "" + Ipiterator.next();

            System.out.println("ip " + ip);

            try {

                System.out.println("开始扫描 ");


                Set key = paramterDevMap.keySet();

                Iterator iterator = key.iterator();

                while (iterator.hasNext()) {
                    String dev = "" + iterator.next();
                    List<TBaParameter> list = paramterDevMap.get(dev);

                    RemoteDevice rd = getRemoteDevice(dev);


                    System.out.println("rd " + rd);


                    if (list != null && !list.isEmpty()) {


                        for (TBaParameter parameter : list) {
                            if (parameter.getType().equals("BV")) {
                                System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));

                                // System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.presentValue, null));
                            } else {
                                System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, parameter.getBaIndex()), PropertyIdentifier.objectName, null));

                            }

                        }
                    }


                }

            /*

            d.startRemoteDeviceDiscovery(new Consumer<RemoteDevice>() {
                @Override
                public void accept(RemoteDevice remoteDevice) {

                    System.out.println("扫描数据--------------- "+remoteDevice.getInstanceNumber()+"  address  "+remoteDevice.getAddress()+"  name "+remoteDevice.getName());





                }




            });

             */
                // d.startRemoteDeviceDiscovery();


                //Thread.sleep(1000);


                // d.terminate();
            } catch (Exception e) {

                e.printStackTrace();

            } finally {


                if (d != null) {
                    System.out.println("关闭bacnet");
                    d.terminate();
                    System.out.println("关闭bacnet 成功");
                }

            }

        }


    }

    /**
     * 创建读取通道
     *
     * @param broadcast
     * @return
     */
    public LocalDevice localDeviceConnect(String broadcast) {
        LocalDevice device = null;

        try {
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    .withLocalBindAddress("10.1.9.250")//本机的ip
                    .withBroadcast(broadcast, 255)//广播地址
                    .withPort(47808)//默认udp端口
                    .withReuseAddress(true)
                    .build();

            device = new LocalDevice(1000000, new DefaultTransport(ipNetwork));

            //初始化
            device.initialize();

        } catch (Exception e) {
            e.printStackTrace();
            if (device != null) {
                device.terminate();
            }
        }
        return device;


    }

    /**
     * 处理时长记录
     *
     * @param parameter
     * @param nowValue
     */
    private void checkDuration(TBaParameter parameter, String nowValue) {

        String ds = parameter.getDurationStatistics();

        if (StringUtils.isEmpty(ds)) return;

        boolean canSave = false;

        if (ds.equals("1") && nowValue.equals("0")) {
            canSave = true;

        } else if (ds.equals("2") && nowValue.equals("1")) {
            canSave = true;
        } else if (ds.equals("3")) {

            canSave = true;
        }

        if (canSave) {
            TBaParameterDuration duration = new TBaParameterDuration();

            duration.setParameterId(parameter.getId());
            duration.setBegtime(parameter.getLasttime());
            duration.setEndtime(new Date());

            int time = (int) ((duration.getEndtime().getTime() - duration.getBegtime().getTime()) / 1000);

            duration.setDuration(time);
            duration.setValue(nowValue);

            dataService.saveBADuration(duration);

        }


    }


    public static LocalDevice getLocalDevice() throws Exception {

        if (d != null) return d;
        //创建网络对象
        IpNetwork ipNetwork = new IpNetworkBuilder()
                .withLocalBindAddress("172.21.122.100")//本机的ip
                //.withLocalBindAddress("10.1.9.234")//本机的ip
                //.withLocalBindAddress("10.1.8.234")//本机的ip
                //.withLocalBindAddress("192.168.43.1")//本机的ip
                //.withLocalBindAddress("192.168.188.1")//本机的ip
                .withSubnet("255.255.255.0", 24)  //掩码和长度，如果不知道本机的掩码和长度的话，可以使用后面代码的工具类获取
                .withPort(47808) //Yabe默认的UDP端口
                .withReuseAddress(true)
                .build();
        //创建虚拟的本地设备，deviceNumber随意
        d = new LocalDevice(2000001, new DefaultTransport(ipNetwork));


        d.initialize();


        // d.startRemoteDeviceDiscovery();

        d.startRemoteDeviceDiscovery(new Consumer<RemoteDevice>() {
            @Override
            public void accept(RemoteDevice remoteDevice) {

                System.out.println("扫描数据--------------- " + remoteDevice.getInstanceNumber() + "  address  " + remoteDevice.getAddress() + "  name " + remoteDevice.getName());

                remoteDeviceMap.put(remoteDevice.getInstanceNumber(), remoteDevice);
            }


        });

        return d;
    }

    /**
     * 获取远程设备
     *
     * @param dev 远程设备ID
     * @return
     * @throws Exception
     */
    public static RemoteDevice getRemoteDevice(String dev) throws Exception {
        RemoteDevice rd = null;
        if (d == null) {
            d = getLocalDevice();
        }
        rd = remoteDeviceMap.get(Integer.parseInt(dev)); //d.getRemoteDeviceBlocking(Integer.parseInt(dev));//获取远程设备，instanceNumber 是设备的device id

        if (rd == null) {
            // 再次检查 d 是否初始化成功
            if (d == null) {
                d = getLocalDevice();
            }
            rd = d.getRemoteDeviceBlocking(Integer.parseInt(dev));//获取远程设备

            if (rd != null) {
                remoteDeviceMap.put(Integer.parseInt(dev), rd);
            }
        }
        return rd;
    }


}
