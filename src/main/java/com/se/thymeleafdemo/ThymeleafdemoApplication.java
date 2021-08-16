package com.se.thymeleafdemo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.socket.WebSocketSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThymeleafdemoApplication {

	public static ArrayList<WebSocketSession> listSession=new ArrayList<WebSocketSession>();
	public static void main(String[] args) {
		SpringApplication.run(ThymeleafdemoApplication.class, args);
	}

}
