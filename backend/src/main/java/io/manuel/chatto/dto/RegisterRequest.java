package io.manuel.chatto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
	@NotBlank
	@Size(min = 3, max = 50)
	String username,
	
	@NotBlank
	@Email
	String email,
	
	@NotBlank
	@Size(max = 12)
	String password
) {}
