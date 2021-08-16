package com.se.thymeleafdemo.controller.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.se.thymeleafdemo.controller.android.ListWebSocketSession;
import com.se.thymeleafdemo.entity.TinNhan;
import com.se.thymeleafdemo.service.TinNhanService;

@Controller
public class DemoController {

	// create a mapping for "/hello"

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private TinNhanService service;
	@GetMapping("/hello")
	public String sayHello(Model theModel) {

		theModel.addAttribute("theDate", new java.util.Date());

		messagingTemplate.convertAndSend("/topic/publicChatRoom",
				new TinNhan(0, "API", "hack nè", "CHAT", LocalDate.now().toString(), "web"));

		return "helloworld";
	}

	@GetMapping("/index")
	public String inDex1(Model theModel) {
		theModel.addAttribute("theDate", new java.util.Date());
		
		return service.findAll().toString();
	}
	@GetMapping("/index2")
	public void inDex2(Model theModel) {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			List<TinNhan> listTNnew = service.findNew();

			try {
				for (WebSocketSession ss : ListWebSocketSession.listWebSocketSession) {

					if (ss.isOpen()) {

						for (int j = listTNnew.size()-1; j >= 0; j--) {

							ss.sendMessage(new TextMessage(String.valueOf((listTNnew.get(j).getId())) + "/"
									+ listTNnew.get(j).getUsername() + ":" + listTNnew.get(j).getNoidung()));
							// System.out.println("Gửi tin"+listTNnew.get(j).getId());
						}
						
					} else {
						//ListWebSocketSession.listWebSocketSession.remove(ss);
					}

				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
