package io.manuel.chatto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
public class ChatController {

	@GetMapping("/test")
	public ResponseEntity<String> testProtected() {
		return ResponseEntity.ok("Accesso riuscito! JWT valido.");
	}
}
