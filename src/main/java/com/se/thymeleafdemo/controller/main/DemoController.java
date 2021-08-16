package com.se.thymeleafdemo.controller.main;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String inDex2(Model theModel) {
		theModel.addAttribute("theDate", new java.util.Date());
		
		return service.findFirt().toString();
	}

}
