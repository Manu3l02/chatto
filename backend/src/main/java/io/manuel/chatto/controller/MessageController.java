package io.manuel.chatto.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.manuel.chatto.dto.MessageRequest;
import io.manuel.chatto.dto.MessageResponse;
import io.manuel.chatto.service.MessageService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/messages")
public class MessageController {
	
	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping
	public ResponseEntity<MessageResponse> sendMessage(
			@Valid @RequestBody MessageRequest request,
			@AuthenticationPrincipal String email
	){
		MessageResponse response = 
				messageService.sendMessage(request.chatId(), email, request.content());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Page<MessageResponse>> getMessages(
		@PathVariable Long chatId,
		Pageable pageable,
		@AuthenticationPrincipal String email
	){
		Page<MessageResponse> messages = 
				messageService.getMessages(chatId, pageable, email);
		return ResponseEntity.ok(messages);
	}
	
	
}
