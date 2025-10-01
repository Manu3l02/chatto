package io.manuel.chatto.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.manuel.chatto.dto.ChatResponse;
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
	
	public List<ChatResponse> getChatsForCurrentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		List<Chat> chats = chatRepo.findByMembersContaining(currentUser);
		
		return chats.stream().map(chat -> {
			String chatName = chat.getName();
			if (!chat.isGroup() && (chatName == null || chatName.isBlank())) {
					chatName = chat.getMembers().stream()
						.filter(m -> !m.getEmail().equals(currentUser.getEmail()))
						.map(User::getUsername)
						.findFirst().orElse("Unknown");
			}
			return new ChatResponse(chat.getId(), chatName, chat.isGroup());
		}).toList();	
	}
}
