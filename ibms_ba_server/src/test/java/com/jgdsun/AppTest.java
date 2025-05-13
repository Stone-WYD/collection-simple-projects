package com.jgdsun;


import com.jgdsun.ba.utils.HexUtils;
import com.jgdsun.ba.utils.SHA256;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.RequestUtils;
import org.junit.Test;


import java.util.function.Consumer;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void test1() throws Exception {

       // new DataService().testDeRoom();


    String str = "HANVONANDCHENRONG202111171758HPO";





    }


    @Test
    public void testBacnet() throws Exception
    {
        LocalDevice d = null;
        try {

            //创建网络对象
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    .withLocalBindAddress("10.1.9.250")//本机的ip
                    //.withLocalBindAddress("10.1.9.234")//本机的ip
                    //.withLocalBindAddress("10.1.8.234")//本机的ip
                    //.withLocalBindAddress("192.168.43.1")//本机的ip
                    //.withLocalBindAddress("192.168.188.1")//本机的ip
                    .withSubnet("255.255.255.0", 24)  //掩码和长度，如果不知道本机的掩码和长度的话，可以使用后面代码的工具类获取
                    .withPort(47808) //Yabe默认的UDP端口
                    .withReuseAddress(true)
                    .build();
            //创建虚拟的本地设备，deviceNumber随意
            d = new LocalDevice(123, new DefaultTransport(ipNetwork));


            d.initialize();


           // d.startRemoteDeviceDiscovery();





            d.startRemoteDeviceDiscovery(new Consumer<RemoteDevice>() {
                @Override
                public void accept(RemoteDevice remoteDevice) {

                    System.out.println("扫描数据 "+remoteDevice.getInstanceNumber()+"  address  "+remoteDevice.getAddress()+"  name "+remoteDevice.getName());

                }


            });



            try {
                Thread.sleep(10000);
            }catch(Exception e)
            {

            }
            RemoteDevice rd = d.getRemoteDeviceBlocking(240090);//获取远程设备，instanceNumber 是设备的device id

            System.out.println("modelName=" + rd.getDeviceProperty( PropertyIdentifier.modelName));

            System.out.println("analogInput2 name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 2), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------analogInput2= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 2), PropertyIdentifier.presentValue, null));


            System.out.println("objectName=" + rd.getDeviceProperty( PropertyIdentifier.objectName));




            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 7), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 7), PropertyIdentifier.presentValue, null));


            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 8), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 8), PropertyIdentifier.presentValue, null));


            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.presentValue, null));







            /*

            List<ObjectIdentifier> objectList =  RequestUtils.getObjectList(d, rd).getValues();

            //打印所有的Object 名称
            for(ObjectIdentifier o : objectList){

                System.out.println(o);
                System.out.println(o.getObjectType()+""+o.getInstanceNumber());
            }


            ObjectIdentifier oid = new ObjectIdentifier(ObjectType.analogInput, 0);
            ObjectIdentifier oid1 = new ObjectIdentifier(ObjectType.analogInput, 1);
            ObjectIdentifier oid2 = new ObjectIdentifier(ObjectType.analogInput, 2);

            //获取指定的presentValue
            PropertyValues pvs = RequestUtils.readOidPresentValues(d, rd, Arrays.asList(oid,oid1,oid2), new ReadListener(){
                @Override
                public boolean progress(double progress, int deviceId,
                                        ObjectIdentifier oid, PropertyIdentifier pid,
                                        UnsignedInteger pin, Encodable value) {
                    System.out.println("========");
                    System.out.println("progress=" + progress);
                    System.out.println("deviceId=" + deviceId);
                    System.out.println("oid="+oid.toString());
                    System.out.println("pid="+pid.toString());
                    System.out.println("UnsignedInteger="+pin);
                    System.out.println("value="+value.toString() + "  getClass =" +value.getClass());
                    return false;
                }

            });
            Thread.sleep(3000);
            System.out.println("analogInput:0 == " + pvs.get(oid, PropertyIdentifier.presentValue));
            //获取指定的presentValue
            PropertyValues pvs2 = RequestUtils.readOidPresentValues(d, rd,Arrays.asList(oid,oid1,oid2),null);
            System.out.println("analogInput:1 == " + pvs2.get(oid1, PropertyIdentifier.presentValue));


            */

            d.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            if(d != null){
                d.terminate();
            }
        }


    }



    @Test
    public void testBacnet3() throws Exception
    {
        LocalDevice d = null;
        try {

            //创建网络对象
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    .withLocalBindAddress("192.168.9.249")//本机的ip
                    //.withLocalBindAddress("10.1.9.234")//本机的ip
                    //.withLocalBindAddress("10.1.8.234")//本机的ip
                    //.withLocalBindAddress("192.168.43.1")//本机的ip
                    //.withLocalBindAddress("192.168.188.1")//本机的ip
                    .withSubnet("255.255.255.0", 24)  //掩码和长度，如果不知道本机的掩码和长度的话，可以使用后面代码的工具类获取
                    .withPort(47808) //Yabe默认的UDP端口
                    .withReuseAddress(true)
                    .build();
            //创建虚拟的本地设备，deviceNumber随意
            d = new LocalDevice(123, new DefaultTransport(ipNetwork));


            d.initialize();


            // d.startRemoteDeviceDiscovery();





            d.startRemoteDeviceDiscovery(new Consumer<RemoteDevice>() {
                @Override
                public void accept(RemoteDevice remoteDevice) {

                    System.out.println("扫描数据 "+remoteDevice.getInstanceNumber()+"  address  "+remoteDevice.getAddress()+"  name "+remoteDevice.getName());

                }


            });



            try {
                Thread.sleep(10000);
            }catch(Exception e)
            {

            }
            RemoteDevice rd = d.getRemoteDeviceBlocking(240013);//获取远程设备，instanceNumber 是设备的device id

            System.out.println("modelName=" + rd.getDeviceProperty( PropertyIdentifier.modelName));

            System.out.println("analogInput2 name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 10), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------analogInput2= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 10), PropertyIdentifier.presentValue, null));


            System.out.println("objectName=" + rd.getDeviceProperty( PropertyIdentifier.objectName));



            /*
            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 7), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 7), PropertyIdentifier.presentValue, null));


            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 8), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 8), PropertyIdentifier.presentValue, null));


            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.presentValue, null));


             */






            /*

            List<ObjectIdentifier> objectList =  RequestUtils.getObjectList(d, rd).getValues();

            //打印所有的Object 名称
            for(ObjectIdentifier o : objectList){

                System.out.println(o);
                System.out.println(o.getObjectType()+""+o.getInstanceNumber());
            }


            ObjectIdentifier oid = new ObjectIdentifier(ObjectType.analogInput, 0);
            ObjectIdentifier oid1 = new ObjectIdentifier(ObjectType.analogInput, 1);
            ObjectIdentifier oid2 = new ObjectIdentifier(ObjectType.analogInput, 2);

            //获取指定的presentValue
            PropertyValues pvs = RequestUtils.readOidPresentValues(d, rd, Arrays.asList(oid,oid1,oid2), new ReadListener(){
                @Override
                public boolean progress(double progress, int deviceId,
                                        ObjectIdentifier oid, PropertyIdentifier pid,
                                        UnsignedInteger pin, Encodable value) {
                    System.out.println("========");
                    System.out.println("progress=" + progress);
                    System.out.println("deviceId=" + deviceId);
                    System.out.println("oid="+oid.toString());
                    System.out.println("pid="+pid.toString());
                    System.out.println("UnsignedInteger="+pin);
                    System.out.println("value="+value.toString() + "  getClass =" +value.getClass());
                    return false;
                }

            });
            Thread.sleep(3000);
            System.out.println("analogInput:0 == " + pvs.get(oid, PropertyIdentifier.presentValue));
            //获取指定的presentValue
            PropertyValues pvs2 = RequestUtils.readOidPresentValues(d, rd,Arrays.asList(oid,oid1,oid2),null);
            System.out.println("analogInput:1 == " + pvs2.get(oid1, PropertyIdentifier.presentValue));


            */

            d.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            if(d != null){
                d.terminate();
            }
        }


    }


    @Test
    public void testBacnet2() throws Exception
    {

        LocalDevice d = null;
        try {

            //创建网络对象
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    .withLocalBindAddress("10.1.9.250")//本机的ip
                    //.withLocalBindAddress("10.1.9.234")//本机的ip
                    //.withLocalBindAddress("10.1.8.234")//本机的ip
                    //.withLocalBindAddress("192.168.43.1")//本机的ip
                    //.withLocalBindAddress("192.168.188.1")//本机的ip
                   // .withSubnet("255.255.255.0", 24)  //掩码和长度，如果不知道本机的掩码和长度的话，可以使用后面代码的工具类获取
                    .withBroadcast("10.1.9.90",255)
                    .withPort(47808) //Yabe默认的UDP端口
                    .withReuseAddress(true)
                    .build();
            //创建虚拟的本地设备，deviceNumber随意
            d = new LocalDevice(123, new DefaultTransport(ipNetwork));


            d.initialize();


            // d.startRemoteDeviceDiscovery();





            d.startRemoteDeviceDiscovery(new Consumer<RemoteDevice>() {
                @Override
                public void accept(RemoteDevice remoteDevice) {

                    System.out.println("扫描数据--------------- "+remoteDevice.getInstanceNumber()+"  address  "+remoteDevice.getAddress()+"  name "+remoteDevice.getName());

                }


            });



            try {
                Thread.sleep(10000);
            }catch(Exception e)
            {

            }
            RemoteDevice rd = d.getRemoteDeviceBlocking(240090);//获取远程设备，instanceNumber 是设备的device id

            System.out.println("modelName=" + rd.getDeviceProperty( PropertyIdentifier.modelName));

            System.out.println("analogInput2 name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 2), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------analogInput2= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.analogValue, 2), PropertyIdentifier.presentValue, null));


            System.out.println("objectName=" + rd.getDeviceProperty( PropertyIdentifier.objectName));




            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 7), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 7), PropertyIdentifier.presentValue, null));


            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 8), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 8), PropertyIdentifier.presentValue, null));


            System.out.println("binaryValue name = " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.objectName, null));

            System.out.println("=-------------------binaryValue= " + RequestUtils.readProperty(d, rd, new ObjectIdentifier(ObjectType.binaryValue, 9), PropertyIdentifier.presentValue, null));







            /*

            List<ObjectIdentifier> objectList =  RequestUtils.getObjectList(d, rd).getValues();

            //打印所有的Object 名称
            for(ObjectIdentifier o : objectList){

                System.out.println(o);
                System.out.println(o.getObjectType()+""+o.getInstanceNumber());
            }


            ObjectIdentifier oid = new ObjectIdentifier(ObjectType.analogInput, 0);
            ObjectIdentifier oid1 = new ObjectIdentifier(ObjectType.analogInput, 1);
            ObjectIdentifier oid2 = new ObjectIdentifier(ObjectType.analogInput, 2);

            //获取指定的presentValue
            PropertyValues pvs = RequestUtils.readOidPresentValues(d, rd, Arrays.asList(oid,oid1,oid2), new ReadListener(){
                @Override
                public boolean progress(double progress, int deviceId,
                                        ObjectIdentifier oid, PropertyIdentifier pid,
                                        UnsignedInteger pin, Encodable value) {
                    System.out.println("========");
                    System.out.println("progress=" + progress);
                    System.out.println("deviceId=" + deviceId);
                    System.out.println("oid="+oid.toString());
                    System.out.println("pid="+pid.toString());
                    System.out.println("UnsignedInteger="+pin);
                    System.out.println("value="+value.toString() + "  getClass =" +value.getClass());
                    return false;
                }

            });
            Thread.sleep(3000);
            System.out.println("analogInput:0 == " + pvs.get(oid, PropertyIdentifier.presentValue));
            //获取指定的presentValue
            PropertyValues pvs2 = RequestUtils.readOidPresentValues(d, rd,Arrays.asList(oid,oid1,oid2),null);
            System.out.println("analogInput:1 == " + pvs2.get(oid1, PropertyIdentifier.presentValue));


            */

            d.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            if(d != null){
                d.terminate();
            }
        }


    }

    @Test
    public void testBacnet4() throws Exception
    {

        LocalDevice d = null;
        try {

            //创建网络对象
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    .withLocalBindAddress("192.168.9.249")//本机的ip
                    //.withLocalBindAddress("10.1.9.234")//本机的ip
                    //.withLocalBindAddress("10.1.8.234")//本机的ip
                    //.withLocalBindAddress("192.168.43.1")//本机的ip
                    //.withLocalBindAddress("192.168.188.1")//本机的ip
                     .withSubnet("255.255.255.0", 24)  //掩码和长度，如果不知道本机的掩码和长度的话，可以使用后面代码的工具类获取
                    //.withBroadcast("10.1.9.90",255)
                    .withPort(47808) //Yabe默认的UDP端口
                    .withReuseAddress(true)
                    .build();
            //创建虚拟的本地设备，deviceNumber随意
            d = new LocalDevice(123, new DefaultTransport(ipNetwork));


            d.initialize();



            d.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            if(d != null){
                d.terminate();
            }
        }


    }



    @Test
    public void testWebSocket() throws Exception {

       String pswd = SHA256.getSHA256StrJava("123456");

       System.out.println("pswd "+pswd);

       //new MQTTService("");

        /*

        try {

            MyWebSocketClient client = new MyWebSocketClient(new URI("ws://localhost:8000/PrintingInkIOT/managers/imserver/11"));
            client.connect();
            while (!client.getReadyState().equals(ReadyState.OPEN)) {
                System.out.println("还没有打开");
                Thread.sleep(100);
            }
            System.out.println("建立websocket连接");

            TestMessage testMessage = new TestMessage();

            testMessage.setContentText("测试内容");
            testMessage.setToUserId("10");

            client.send(new Gson().toJson(testMessage));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


         */


        // 往websocket服务端发送数据
        //  myClient.send("此为要发送的数据内容");


    }

    @Test
    public void read() throws Exception {


        LocalDevice device = null;

        try
        {
            //device = localDeviceConnect("10.1.9.85");
            device = localDeviceConnect("10.1.9.85");
            //获取远程连接设备

            device.startRemoteDeviceDiscovery();

           // RemoteDevice rd = device.getRemoteDeviceBlocking(240013);
            RemoteDevice rd = device.getRemoteDeviceBlocking(240013);

           // rd.(new ObjectIdentifier(ObjectType.device,30));

            System.out.println(rd.toString());


            System.out.println("=-------------------analogInput2= " + RequestUtils.readProperty(device, rd, new ObjectIdentifier(ObjectType.binaryInput, 1), PropertyIdentifier.presentValue, null));
           // rd.get

            //组装请求
            System.out.println("binaryValue name = " + RequestUtils.readProperty(device, rd, new ObjectIdentifier(ObjectType.binaryInput, 1), PropertyIdentifier.objectName, null));

          //  System.out.println("=-------------------analogInput2= " + RequestUtils.readProperty(device, rd, new ObjectIdentifier(ObjectType.analogValue, 2), PropertyIdentifier.presentValue, null));


           // System.out.println("objectName=" + rd.getDeviceProperty( PropertyIdentifier.objectName));


        }catch (Exception e)
        {
            e.printStackTrace();
            if(device!=null)
            {
                device.terminate();
            }
        }

        /*

        try {

            MyWebSocketClient client = new MyWebSocketClient(new URI("ws://localhost:8000/PrintingInkIOT/managers/imserver/11"));
            client.connect();
            while (!client.getReadyState().equals(ReadyState.OPEN)) {
                System.out.println("还没有打开");
                Thread.sleep(100);
            }
            System.out.println("建立websocket连接");

            TestMessage testMessage = new TestMessage();

            testMessage.setContentText("测试内容");
            testMessage.setToUserId("10");

            client.send(new Gson().toJson(testMessage));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


         */


        // 往websocket服务端发送数据
        //  myClient.send("此为要发送的数据内容");


    }

    public LocalDevice localDeviceConnect(String broadcast)
    {
        LocalDevice device = null;

        try
        {
            IpNetwork ipNetwork = new IpNetworkBuilder()
                    .withBroadcast(broadcast,255)//广播地址
            .withPort(47808)//默认udp端口
            .withReuseAddress(true)
                    .build();

            device = new LocalDevice(123,new DefaultTransport(ipNetwork));

            //初始化
            device.initialize();

        }catch (Exception e)
        {
            e.printStackTrace();
            if(device!=null)
            {
                device.terminate();
            }
        }
        return device;


    }

    @Test
    public void test3()
    {
        String str = "123456";

      //  byte zt = "57AB0001";

        byte datab[] = str.getBytes();

        byte sendData[] = new byte[datab.length+6];

        sendData[0] = 0x57;
        sendData[1] = (byte)0xAB;

        sendData[2] = 0;
        sendData[3] = 2;

        sendData[4] = 8;

        sendData[5] = 0;
        sendData[6] = 0;



        System.arraycopy(datab,0,sendData,5,datab.length);

        int sum = 0;
        for(int i=0;i<sendData.length - 1;i++)
        {
            sum = sum+(sendData[i]&0xFF);
        }
        sendData[sendData.length-1] = (byte)sum;

        System.out.println(HexUtils.bytesToHexString(sendData));


        
        /*
        String url = "http://wewfewefwfe:80000/test1.jpg".toUpperCase();

        if(url.startsWith("HTTP://") || url.startsWith("HTTPS://")) {

            boolean canDo = false;

            int index1 = url.indexOf(":");
            int index2 = url.lastIndexOf(":");

            System.out.println(index1);


            if (index1 != index2) {
                String url2 = url.substring(index2 + 1);

                int index3 = url2.indexOf("/");

                System.out.println("index3 "+index3);

                if (index3 > 0) {
                    String port = url2.substring(0, index3);

                    System.out.println("port "+port);

                    if (port.length() < 8) {
                        //0~65535

                        try {
                            int _port = Integer.parseInt(port);


                                canDo = true;


                        } catch (Exception e) {

                        }
                    }
                }
            } else {
                canDo = true;
            }

            System.out.println(canDo);
        }

         */
    }
    
    @Test
    public void testInfo()
    {
        String str = "yLP7FLicAhESa3V0FBt/A6ayNI8ypngUD5DUpwTzDXjX965PbxdWVgVPewIvOZla8i1UHMwk+8B0zNWOKTUYzRPh769RIEz4tiJ85pWXMEwHk8HstV2ISJJIIt8JrSDXxdhbOQnHhqOUTe2KNiNDhlAs3+2SK0zaZBY2/xe29jippy//PMmWTo0aVJjxhOeCbn4Z7JbaziCHkHm03LZjBCIUmsuZhoA8+1pS4hqY/AWBrCT6tgnMwZRgYBU8jKHVJXVwm7v4IDloGG4ToMrg5fWEayKssYhX9xidnHP9ZsqJ+LG/Gj7vM4Avz5BPLTMgOgUkW76CUTZW9EnVTuugtpKPgiAA4RJLOTB8hfAuITgbm/pIine1onnLkABWT3vXFtIla2/CPGzBvKJcYIpcrxhZDgY3hfSjqIJa8WinZk+nyYVVo1Qc0kBl8I5x1NGIEzzTX2E0I42eYPfmU3EPzbyVcYOud7twEf3r9U/dTs8jUpHNNqwR6sssHh/IfpsgghmLYi5zZ4qn/TWhV5dBYtjthdLEf+LZ4nsei2KhsJEZOtNiiQExScg8J//ZRU+jY2X9t/X655i7oU/4LKz7ftebu3rCA4wEV5ZmrW09MOr/dCAYHV7PVYesFHybx2kVGLcJNU+UTYNCgb7xHNhR8ijO8oGl+xMBFMwYscnYTBM46zaoC239VBmwqM8/ZIEOKFXlpww/A+ssjveFHlPv/BLN4oj+0eeZp3Re7foTfZRPE9JKvREAFPtB7J35D7QDyMgWgVStNroWfG+uQ5I1D12NjYGH3AdYQSU27jPvyBohhFW2HTHNHadtxazZmyYODf4anOzoQ9rPNaUeey6FUC6k7mG/x4VJORHGe5Ms956TxJrGEX1FYnLKXF+kALPMZQUqsO3hBlnY3Kirc4rOQGvcWaMmVbvEW7PXD0/Q3vn10mhJe41NbW+cLijhgemjttgHsskq0VlIn78dQbQOJOf49Cb5RAJ+NiQ7O1vkTMI=";


        String key = "567502e0e087c22b";

        /*
        try {
            String str2 = AESUtil.decrypt(str);
            System.out.println(str2);
        }catch(Exception e)
        {

        }

         */



    }




}
