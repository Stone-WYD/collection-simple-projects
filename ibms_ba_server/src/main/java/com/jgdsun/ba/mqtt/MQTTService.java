/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.jgdsun.ba.mqtt;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jgdsun.ba.control.BaService;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.service.DataService;
import com.jgdsun.ba.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class MQTTService {



    public static final String TAG = MQTTService.class.getSimpleName();


    private static MqttAsyncClient client;
    private MqttConnectOptions connOpts;

    private DataService dataService;

    private Gson gson;

    private MQTTListener mqttListener;


    private String broker = "tcp://127.0.0.1:1883";///mqtt";
//    private String broker = "tcp://192.168.20.200:1883";///mqtt";

   //private String host = "ws://192.168.1.9:8083/mqtt";///mqtt";
    private String userName = "test1";
    private String passWord = "123456";

   // private static String loginTopic = "login";

    /**订阅*/
    private static String posterTopic = "test";      //

    /**动环空调开关订阅*/
    private static String deAirconditioningControlTopic = "de/airconditioning/control";

    /**空调设置订阅*/
    private static String deAirconditioningSetTopic = "de/airconditioning/set";

    /**BA控制订阅*/
    private static String baControlTopic = "ba/control";

    /**BA变更推送订阅*/
    private static String baValueChangeTopic = "ba/change";





    private String clientId = UUID.randomUUID().toString();//.randomUUID();//客户端标识
    private IGetMessageCallBack IGetMessageCallBack;

    public static final int THREAD_COUNT = 100;

    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
    public interface IGetMessageCallBack {
        public void setMessage(String message);
    }

    public MQTTService(MQTTListener mqttListener) {

        this.mqttListener = mqttListener;

        dataService = new DataService();

        gson = new Gson();


       //loginTopic = "door"+"/login";
      //  posterTopic = "door"+"/poster";

        propertiesUtil = new PropertiesUtil(System.getProperty("user.dir")+ File.separator+"config.properties");

        initParam();






        init();


        new Thread()
        {
            public void run()
            {

                while(!isFirstConnetct) {
                    try {
                        sleep(30 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("mqtt 执行一次连接");

                    if(isFirstConnetct)return;
                    doClientConnection();
                }

            }
        }.start();


    }
    /**
     * 初始化连接信息
     */
    private void initParam() {

        broker = getStringData("mqtt.broker");


        userName = getStringData("mqtt.username");
        passWord = getStringData("mqtt.password");

        String clientid = getStringData("mqtt.clientid");


        if(TextUtils.isEmpty(clientid))
        {
            clientid = getMqttId();

            propertiesUtil.writeProperty("mqtt.clientid",clientid);
        }

        this.clientId = clientid;
    }


    /**
     * 获取mqtt id
     * @return
     */
    public static String getMqttId()
    {
        String mqttid = UUID.randomUUID().toString();

        System.out.println("mqttid "+mqttid);

        return mqttid;


    }

    /**首次连接是否成功*/
    boolean isFirstConnetct = false;





    /**
     * 判断是否连接
     * @return
     */
    public static boolean isConnected()
    {
        if (client != null){

            try {

                return client.isConnected();
            }catch(Exception e)
            {

            }
        }
        return false;
    }

    /**
     * 发布信息
     * @param topic
     * @param msg
     */
    public static void publish(String topic,String msg){
        //String topic = posterTopic;
        Integer qos = 1;
        Boolean retained = false;
        try {
            if (client != null){

                System.out.println("mqtt 连接状态 "+client.isConnected());

                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                System.out.println("mqtt 发送成功 ");
            }
        } catch (MqttException e) {

            System.out.println("mqtt 异常 "+e.getMessage());
            e.printStackTrace();
        }catch(Exception e)
        {

        }
    }

    private void init() {

        try {

            MemoryPersistence persistence = new MemoryPersistence();
             client = new MqttAsyncClient(broker, clientId, persistence, new TimerPingSender());
           // CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT);
             connOpts = new MqttConnectOptions();
            connOpts.setMaxInflight(THREAD_COUNT * 10);
            connOpts.setCleanSession(false);
            connOpts.setAutomaticReconnect(true);
            connOpts.setKeepAliveInterval(30);

            connOpts.setUserName(userName);
            connOpts.setPassword(passWord.toCharArray());

            client.setCallback(new MqttCallbackExtended() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("连接失败");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    System.out.println("topic "+topic+"  "+message.toString());

                    try {

                        if (topic.equals(deAirconditioningControlTopic)) {
                            //空调开关控制
                            System.out.println("空调开关控制 ");

                            String msg = message.toString();

                         //   AirconditioningControlBean controlBean = gson.fromJson(msg, AirconditioningControlBean.class);

                         //   controlListener.controlAirConditioning(controlBean);

                        } else if (topic.equals(deAirconditioningSetTopic)) {
                            System.out.println("空调设置控制 ");

                            String msg = message.toString();

                          //  AirconditioningSetBean setBean = gson.fromJson(msg, AirconditioningSetBean.class);


                           // controlListener.setAirConditioning(setBean);


                        } else if (topic.equals(baControlTopic)) {
                            System.out.println("BA控制");
                            JSONObject jsonObject = JSON.parseObject(message.toString());
                            String id = jsonObject.getString("id");

                            String value = jsonObject.getString("value");
                            TBaParameter parameter = dataService.getAllParameterById(id);
                            mqttListener.writeOne(parameter,value);
                        }
                    }catch(Exception e)
                    {
                        log.error(e.getMessage());
                    }


                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //countDownLatch.countDown();
                }

                @Override
                public void connectComplete(boolean reconnect, String serverURI) {

                    isFirstConnetct = true;


                    try {
                        String subs[] = {posterTopic};
                        int qos[] = {1,};
                        // 订阅myTopic话题
                        client.subscribe(deAirconditioningControlTopic,1);//订阅一个
                        client.subscribe(deAirconditioningSetTopic,1);//订阅一个
                        client.subscribe(baControlTopic,1);//订阅一个
                        client.subscribe(baValueChangeTopic,1);//订阅一个

                        //  client.subscribe(subs,qos);//订阅多个个
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }


                    publish("test","123444466");
                    System.out.println("连接成功");

                    countDownLatch.countDown();

                }
            });
            client.connect(connOpts);
            countDownLatch.await();

            System.out.println("发布完毕!");

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public void onDestroy() {

        try {
            if(client!=null) {
                client.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }catch(Exception e)
        {

        }

    }

    /** 连接MQTT服务器 */
    private void doClientConnection() {

        if(client!=null) {
            if (!client.isConnected()) {
                try {

                        client.connect(connOpts);




                   // System.out.println("mqtt连接成功");
                } catch (MqttException e) {
                    System.out.println("mqtt连接异常"+e.getMessage());
                    e.printStackTrace();
                }catch(Exception e)
                {

                }
            }
        }

    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            //Log.i(TAG, "mqtt 连接成功 ");
            isFirstConnetct = true;
            try {
                String subs[] = {posterTopic};
                int qos[] = {1,};
                // 订阅myTopic话题
                client.subscribe(posterTopic,1);//订阅一个
              //  client.subscribe(subs,qos);//订阅多个个
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            System.out.println("mqtt连接失败 "+arg1.getMessage());
            arg1.printStackTrace();
            // 连接失败，重连
        }


    };





    public void setIGetMessageCallBack(IGetMessageCallBack IGetMessageCallBack){
        this.IGetMessageCallBack = IGetMessageCallBack;
    }



    public  void toCreateNotification(String message){

        /*
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(this,MQTTService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);//3、创建一个通知，属性太多，使用构造器模式

        Notification notification = builder
                .setTicker("测试标题")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("")
                .setContentText(message)
                .setContentInfo("")
                .setContentIntent(pendingIntent)//点击后才触发的意图，“挂起的”意图
                .setAutoCancel(true)        //设置点击之后notification消失
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        startForeground(0, notification);
        notificationManager.notify(0, notification);


         */
    }

    public void doorStatusChange(String doorId)
    {


    }

    /**配置文件读取工具类*/
    private static PropertiesUtil propertiesUtil;

    /**
     * 读取配置文件
     * @param name
     * @return
     */
    public  String getStringData(String name)
    {

        String value = propertiesUtil.readProperty(name);

        if(value==null)value="";


        return value;


    }



}