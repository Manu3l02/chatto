package io.manuel.chatto.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.manuel.chatto.model.Chat;
import io.manuel.chatto.model.User;
import io.manuel.chatto.repository.ChatRepository;
import io.manuel.chatto.repository.UserRepository;

@Service
public class ChatService {
	
	private final ChatRepository chatRepo;
	private final UserRepository userRepo;
	
	public ChatService(ChatRepository chatRepo, UserRepository userRepo) {
		this.chatRepo = chatRepo;
		this.userRepo = userRepo;
	}
	
	public Chat createChat(Set<Long> userIds) {
		Set<User> members = userRepo.findAllById(userIds).stream().collect(Collectors.toSet());
		
		Chat chat = new Chat();
		chat.setMembers(members);
		return chatRepo.save(chat);
	}
}
