package io.manuel.chatto.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.manuel.chatto.dto.MessageResponse;

@RestController
@RequestMapping("/test")
public class WebSocketTestController {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	public WebSocketTestController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@PostMapping("/publish")
	public ResponseEntity<String> publishTestMessage() {
		MessageResponse fakeMessage = new MessageResponse(
				999L, 
				123L, 
				2L, 
				"Tester", 
				"Hello from REST test!", 
				LocalDateTime.now()
		);
		
		messagingTemplate.convertAndSend("/topic/chats" + fakeMessage.chatId(), fakeMessage);
		
		return ResponseEntity.ok("Test message published to /topic/chats" + fakeMessage.chatId());
	}
	
}
