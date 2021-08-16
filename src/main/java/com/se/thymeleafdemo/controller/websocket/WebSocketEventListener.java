package com.se.thymeleafdemo.controller.websocket;

import java.io.IOException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.se.thymeleafdemo.controller.android.ListWebSocketSession;
import com.se.thymeleafdemo.entity.TinNhan;
import com.se.thymeleafdemo.service.TinNhanService;

@Component
@Controller
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private TinNhanService tinNhanService;

	// chạy hàm này để connect khi login (1)
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
		//
		// System.out.println("+++handleWebSocketConnectListener "+event);
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");

		if (username != null) {
			logger.info("User Disconnected : " + username);

			TinNhan tinNhan = new TinNhan();
			tinNhan.setType("LEFT");
			tinNhan.setUsername(username);

			tinNhanService.save(new TinNhan(0, username, "đã thoát !", "LEFT", LocalDate.now().toString(), "web"));
			messagingTemplate.convertAndSend("/topic/publicChatRoom", tinNhan);
//			try {
//				for (WebSocketSession ss : ListWebSocketSession.listWebSocketSession) {
//
//					if(ss.isOpen()) {
//						ss.sendMessage(new TextMessage("300/Hacker:123456789"));
//					}
//					
//
//				}
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}

			//
			// System.out.println("+++handleWebSocketDisconnectListener
			// "+event+"/n+++++++"+tinNhan+messagingTemplate);
		}
	}

}