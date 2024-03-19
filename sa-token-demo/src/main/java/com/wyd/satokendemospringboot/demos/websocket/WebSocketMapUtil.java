package com.wyd.satokendemospringboot.demos.websocket;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: sa-token-demo-springboot
 * @description: websocket工具类
 * @author: Stone
 * @create: 2023-07-20 09:43
 **/
public class WebSocketMapUtil {

    public static ConcurrentHashMap<String, MyWebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, MyWebSocketServer myWebSocketServer){
        webSocketMap.put(key, myWebSocketServer);
    }

    public static MyWebSocketServer get(String key){
        return webSocketMap.get(key);
    }

    public static void remove(String key){
        webSocketMap.remove(key);
    }

    public static Collection<MyWebSocketServer> getValues(){
        return webSocketMap.values();
    }
}
