package com.rizzatto.chat_preview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:/usr/env/chat-preview/application.properties")
public class ChatPreviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatPreviewApplication.class, args);
		System.out.println("SYSTEM SERVICE UP");
	}

}
