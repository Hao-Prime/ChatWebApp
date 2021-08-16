package com.se.thymeleafdemo.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.se.thymeleafdemo.entity.TinNhan;
 
@Controller
public class WebSocketController {
	
	
 
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public TinNhan sendMessage(@Payload TinNhan tinNhan) {
    	//
    	
    	//System.out.println("......sendMessage "+tinNhan);
        return tinNhan;
    }
 
    //Sau đó chạy add user 1.1 đồng thời trả về chatmassage cho tất cả client return là trả về cho các client message
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public TinNhan addUser(@Payload TinNhan tinNhan, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", tinNhan.getUsername());
        //
       // System.out.println("+++addUser "+tinNhan+" \n headerAccessor"+headerAccessor);
        return tinNhan;
    }
 
}