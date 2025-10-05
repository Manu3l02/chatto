package io.manuel.chatto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageRequest(
		@NotNull Long chatId, 
		@NotBlank @Size(max = 3000) String content) 
{}
