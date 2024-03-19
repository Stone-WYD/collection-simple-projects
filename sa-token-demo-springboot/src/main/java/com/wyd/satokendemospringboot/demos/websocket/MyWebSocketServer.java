package com.wyd.satokendemospringboot.demos.websocket;

import cn.hutool.json.JSONObject;
import com.wyd.satokendemospringboot.demos.entity.MyUser;
import com.wyd.satokendemospringboot.demos.service.MyUserService;
import com.wyd.satokendemospringboot.demos.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @program: sa-token-demo-springboot
 * @description: websocket测试服务端代码
 * @author: Stone
 * @create: 2023-07-20 09:40
 **/
@Slf4j
@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocketServer {

    @Resource
    private MyUserService myUserService;

    public MyUser queryById(Long id){
        return myUserService.queryById(id);
    }

    @OnOpen
    public void onOpen(Session session){
        log.info("建立了一个连接，连接id为：" + session.getId());
        WebSocketMapUtil.put(session.getId(), this);
        MyWebSocketServer bean = ApplicationContextUtil.getBeanOfType(MyWebSocketServer.class);
        log.info("当前实例是否为@Component标注的bean：{} ", bean == this);
        /*
        说明新建连接时创建的 MyWebSocketServer 实例不会从Spring的 MyWebSocketServer 单例中拷贝依赖
        MyUser myUser = myUserService.queryById(1L);
        MyUser myUserOfBean = bean.queryById(1L);
        System.out.println(myUser);
        System.out.println(myUserOfBean);
        */
    }

    @OnClose
    public void onClose(Session session){
        log.info("关闭了一个连接，连接id为：" + session.getId());
        WebSocketMapUtil.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.info("有一个连接发生错误，连接id为：" + session.getId());
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String param, Session session) throws IOException {
        log.info("收到连接id为 {} 的客户端信息，收到的信息内容为：{}" ,session.getId(), param);
        String result = "消息已收到";
        sendMessage(session, 1, "成功！", result);
    }

    public void sendMessage(Session session, int status, String message, Object datas) throws IOException{
        JSONObject result = new JSONObject();
        result.putOpt("status", session);
        result.putOpt("datas", datas);
        result.putOpt("message", message);
        session.getBasicRemote().sendText(result.toStringPretty());
    }

    /*
    * 1.根据测试可以知道，@Component和@ServerEndpoint标注的类，确实在Spring启动时创建了一个单例放入其中
    *   而在有新连接时，会再创建实例，但这个实例跟Spring中的这个单例是没有关系的
    * 2.但是 @ServerEndpoint 是jdk的注解，如果只使用这个注解，Spring 中配置的 ServerEndpointExporter 实例是找不到这个类的，
    *   所以还是需要@Component注解来将这个类放入Spring容器中
    * */
}
