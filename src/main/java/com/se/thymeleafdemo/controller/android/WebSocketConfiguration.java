package com.se.thymeleafdemo.controller.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.se.thymeleafdemo.service.TinNhanService;
@Configuration
@EnableWebSocket
// Add this annotation to an @Configuration class to configure processing WebSocket requests
public class WebSocketConfiguration implements WebSocketConfigurer {
	@Autowired
	private TinNhanService tinNhanService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(tinNhanService,messagingTemplate), "/websocket");
    }
}