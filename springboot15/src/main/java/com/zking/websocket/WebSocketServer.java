package com.zking.websocket;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ServerEndpoint("/ws/{id}")
public class WebSocketServer {

    private final double random = Math.random();

    private static Session session ;

    private final ConcurrentHashMap<String , WebSocketServer> users = new ConcurrentHashMap<>();

    //新的ws连接
    @OnOpen
    public void open(Session session, @PathParam("id") Integer id) {
        this.session = session;
        System.out.printf("==========>建立连接[SessionId=%s,id=%s]----[%s]！%n",session.getId(),id,random);
        users.put(session.getId(),this);
    }

    // 关闭WebSockets时调用
    @OnClose
    public void close(Session session) {
        System.out.printf("==========>连接关闭[SessionId=%s]----[%s]！%n",session.getId(),random);
        users.remove(session.getId());
    }

    // WebSocket接收到消息，-【可以删除】，不用WebSocket发送消息，用Post
    @OnMessage
    public void message(Session session, String message) {
        System.out.printf("==========>收到消息[SessionId=%s]：%S----[%s]！%n",session.getId(),message,random);

        //推送消息
        users.forEach((key,value) ->{
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {


            }
        });
    }

    // WebSocket发生错误时-【测试，可以删除】
    @OnError
    public void error(Session session, Throwable error) {
        System.out.println("错误：" + error);
    }
}
