package io.manuel.chatto.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.manuel.chatto.model.User;
import io.manuel.chatto.repository.UserRepository;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {
	
	private final JwtService jwtService;
	private final UserRepository userRepo;
	
	public AuthChannelInterceptor(JwtService jwtService, UserRepository userRepo) {
		this.jwtService = jwtService;
		this.userRepo = userRepo;
	}
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = 
				MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		
		if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
			String authHeader = accessor.getFirstNativeHeader("Authorization");
			
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				
				if (jwtService.validateToken(token)) {
					String email = jwtService.extractEmail(token);
					User user = userRepo.findByEmail(email).orElse(null);
					
					if (user != null) {
						UsernamePasswordAuthenticationToken authentication = 
								new UsernamePasswordAuthenticationToken(email, null, null);
						
						SecurityContextHolder.getContext().setAuthentication(authentication);
						
						accessor.setUser(authentication);
					}
				}
			}
		}
		
		return message;
	}
	
}
