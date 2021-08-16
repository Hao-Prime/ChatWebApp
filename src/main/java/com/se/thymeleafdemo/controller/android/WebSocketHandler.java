package com.se.thymeleafdemo.controller.android;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.se.thymeleafdemo.entity.TinNhan;
import com.se.thymeleafdemo.service.TinNhanService;
import com.se.thymeleafdemo.service.TinNhanServiceImpl;

@Controller
public class WebSocketHandler extends AbstractWebSocketHandler {

	
	private TinNhanService tinNhanService;
	private SimpMessageSendingOperations messagingTemplate;
	private List<TinNhan> listTN=null;
			
	
	public WebSocketHandler(TinNhanService tinNhanService,SimpMessageSendingOperations messagingTemplate) {
		this.tinNhanService=tinNhanService;
		this.messagingTemplate=messagingTemplate;
	}
	

	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		TinNhan tn = new TinNhan();
		String msg = String.valueOf(message.getPayload());

		//System.out.println(msg);
		String type = msg.substring(0, 4);
		String username = msg.substring(4, msg.indexOf(":"));
		String noidung = msg.substring(msg.indexOf(":") + 1, msg.length());

		try {
			switch (type) {
			case ("CONN"):
				
				listTN= tinNhanService.findNew();
				tn=tinNhanService.findById(Integer.parseInt(noidung));
				int index=10;
				for (int i = 0; i < listTN.size(); i++) {
					if(listTN.get(i).getId()==tn.getId()) {
						index=i;
						break;
					}
				}
				for (int j = 0;j<index; j++) {
					session.sendMessage(
							new TextMessage(String.valueOf((listTN.get(j).getId()))+"/"+listTN.get(j).getUsername() + ":" + listTN.get(j).getNoidung()));
					//System.out.println("Gửi tin"+listTN.get(j).getId());
				}
				
				

				break;
			case ("CHAT"):
				tinNhanService.save(new TinNhan(0, username, noidung, type, LocalDate.now().toString(), "app"));
				messagingTemplate.convertAndSend("/topic/publicChatRoom",
						new TinNhan(0, username, noidung, type, LocalDate.now().toString(), "app"));
				break;

			case ("JOIN"):
				tinNhanService.save(new TinNhan(0, username, noidung, type, LocalDate.now().toString(), "app"));
				messagingTemplate.convertAndSend("/topic/publicChatRoom",
						new TinNhan(0, username, " đã tham gia!", type, LocalDate.now().toString(), "app"));
				System.out.println("Connected to Client " + username);
				
				break;
			default:
				System.out.println("Không phiên dịch được app gửi gì");

			}
		} catch (IOException e) {
			tinNhanService.save(new TinNhan(0, username, noidung, "LEFT", LocalDate.now().toString(), "app"));
//			messagingTemplate.convertAndSend("/topic/publicChatRoom",
//					new TinNhan(0, username, noidung, "LEFT", LocalDate.now().toString(), "app"));
			System.out.println("Android " + username + " disconnect...");
		}
	}
}
