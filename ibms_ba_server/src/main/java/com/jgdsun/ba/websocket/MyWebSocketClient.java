package com.jgdsun.ba.websocket;

import java.net.URI;


import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient extends WebSocketClient{

    Logger logger = LogManager.getLogger(MyWebSocketClient.class);
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }
    @Override
    public void onOpen(ServerHandshake arg0) {
        // TODO Auto-generated method stub
       logger.info("------ MyWebSocket onOpen ------");
    }
    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
    // TODO Auto-generated method stub
        logger.info("------ MyWebSocket onClose ------");
    }
    @Override
    public void onError(Exception arg0) {
    // TODO Auto-generated method stub
        logger.info("------ MyWebSocket onError ------");
    }
    @Override
    public void onMessage(String arg0) {
    // TODO Auto-generated method stub
    logger.info("-------- 接收到服务端数据： " + arg0 + "--------");
    }


    /**
     * 发送信息
     * @param url
     * @param webSocketMessage
     */
    public static void sendMsg(String url,WebSocketMessage webSocketMessage)
    {
        try {
            MyWebSocketClient client = new MyWebSocketClient(new URI(url));
            client.connect();

            int errorTimes = 0;

            while (!client.getReadyState().equals(ReadyState.OPEN)) {
                System.out.println("还没有打开");
                Thread.sleep(200);

                errorTimes++;
                if(errorTimes>50)return;
            }
            System.out.println("建立websocket连接");



            client.send(new Gson().toJson(webSocketMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}