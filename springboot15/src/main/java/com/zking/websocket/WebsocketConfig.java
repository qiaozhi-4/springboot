package com.zking.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebsocketConfig {

    // WebSocket唯一配置
    @Bean
    public ServerEndpointExporter exporter() {
        return new ServerEndpointExporter();
    }

}
