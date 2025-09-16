package io.manuel.chatto.dto;

import java.time.LocalDateTime;

public record MessageResponse(
		Long id, 
		Long chatId, 
		Long senderId, 
		String senderUsername, 
		String content,
		LocalDateTime createdAt
		) {

}
