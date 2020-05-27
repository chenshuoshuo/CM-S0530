package com.lqkj.web.gnsc.config;

import com.lqkj.web.gnsc.APIVersion;
import com.lqkj.web.gnsc.modules.handler.WebSocketPushHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketPushHandler webSocketPushHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(webSocketPushHandler, "/socket/" + APIVersion.V1+ "/userReg")
                .setAllowedOrigins("*");
    }
}

