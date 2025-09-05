package io.manuel.chatto.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.manuel.chatto.dto.AuthResponse;
import io.manuel.chatto.dto.LoginRequest;
import io.manuel.chatto.dto.RegisterRequest;
import io.manuel.chatto.dto.UserResponse;
import io.manuel.chatto.model.User;
import io.manuel.chatto.repository.UserRepository;
import io.manuel.chatto.security.JwtService;

@Service
public class UserService {

	private final UserRepository userRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public UserService(
			UserRepository userRepo, 
			BCryptPasswordEncoder passwordEncoder,
			JwtService jwtService) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	public UserResponse registerUser(RegisterRequest request) {
	
		if (userRepo.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email Already Registered");
		}
		
		String cryptedPassword = passwordEncoder.encode(request.password());
		
		User user = new User();
		user.setUsername(request.username());
		user.setEmail(request.email());
		user.setPassword(cryptedPassword);
		
		User saved = userRepo.save(user);
		
		return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail());
	}
	
	public AuthResponse authenticateUser(LoginRequest request) {
		User user = userRepo.findByEmail(request.email())
			.orElseThrow(() -> new RuntimeException("Invalid credential"));
		
		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new RuntimeException("Invalid Credentials");
		}
		
		String token = jwtService.generateToken(user);
		return new AuthResponse(
				token, 
				new UserResponse(user.getId(), user.getUsername(), user.getEmail())
				);
	}
}
