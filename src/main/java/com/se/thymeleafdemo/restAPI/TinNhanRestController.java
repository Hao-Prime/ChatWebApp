package com.se.thymeleafdemo.restAPI;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se.thymeleafdemo.entity.TinNhan;
import com.se.thymeleafdemo.service.TinNhanService;

@RestController
@RequestMapping("/api")
public class TinNhanRestController {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
    private TinNhanService TinNhanService;
    @Autowired
    public TinNhanRestController(TinNhanService theTinNhanService) {
            TinNhanService = theTinNhanService;
    }
   
    @GetMapping("/TinNhans")
    public List<TinNhan> findAll() {
		messagingTemplate.convertAndSend("/topic/publicChatRoom",
				new TinNhan(0, "API", "hack nè", "CHAT", LocalDate.now().toString(), "web"));
        return TinNhanService.findAll();
    }
    
    @PostMapping("/save")
    public List<TinNhan> save(@RequestParam("username") String username,@RequestParam("noidung") String noidung,@RequestParam("type") String type,@RequestParam("date") String date,@RequestParam("brower") String brower) {
    	TinNhanService.save(new TinNhan(0, username,noidung, type,date, brower));
        return TinNhanService.findFirt();
    }

}










