package io.manuel.chatto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.manuel.chatto.dto.ChatRequest;
import io.manuel.chatto.model.Chat;
import io.manuel.chatto.service.ChatService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/chats")
public class ChatController {
	
	private final ChatService chatService;
	
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testProtected() {
		return ResponseEntity.ok("Accesso riuscito! JWT valido.");
	}
	
	@PostMapping("/chat")
	public ResponseEntity<Chat> createChat(@Valid @RequestBody ChatRequest request){
		Chat chat = chatService.createChat(request.userIds());
		return ResponseEntity.ok(chat);
	}
}
