package io.manuel.chatto.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.manuel.chatto.dto.MessageRequest;
import io.manuel.chatto.dto.MessageResponse;
import io.manuel.chatto.service.MessageService;

@Controller
public class ChatSocketController {
	
	private final MessageService messageService;
	private final SimpMessagingTemplate messagingTemplate;
	
	public ChatSocketController(
			MessageService messageService, 
			SimpMessagingTemplate messagingTemplate) {
		this.messageService = messageService;
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/chat.send")
	public void sendMessage(@Payload MessageRequest request, 
							Principal principal) {
		String email = principal.getName();

		MessageResponse saved = 
				messageService.sendMessage(request.chatId(), email, request.content());
		
		messagingTemplate.convertAndSend("/topic/chats" + saved.chatId(), saved);
	}
	
}
