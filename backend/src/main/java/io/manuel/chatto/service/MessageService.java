package io.manuel.chatto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.manuel.chatto.dto.MessageResponse;
import io.manuel.chatto.model.Chat;
import io.manuel.chatto.model.Message;
import io.manuel.chatto.model.User;
import io.manuel.chatto.repository.ChatRepository;
import io.manuel.chatto.repository.MessageRepository;
import io.manuel.chatto.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService {
	
	private final MessageRepository messageRepo;
	private final ChatRepository chatRepo;
	private final UserRepository userRepo;
	
	public MessageService(
			MessageRepository messageRepo, 
			ChatRepository chatRepo, 
			UserRepository userRepo) {
		this.messageRepo = messageRepo;
		this.chatRepo = chatRepo;
		this.userRepo = userRepo;
	}
	
	@Transactional
	public MessageResponse sendMessage(Long chatId, String email, String content) {
		User sender = userRepo.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		Chat chat = chatRepo.findById(chatId)
				.orElseThrow(() -> new EntityNotFoundException("Chat not found"));
		boolean isMember = 
				chat.getMembers().stream().anyMatch(u -> u.getId().equals(sender.getId()));
		if (!isMember) { 
			throw new AccessDeniedException("User is not a member of the chat");
		}
		
		Message message = new Message(content, sender, chat);
		Message saved = messageRepo.save(message);
		return mapToDto(saved);
	}
	
	@Transactional(readOnly = true)
	public Page<MessageResponse> getMessages(Long chatId, Pageable pageable, String email) {
		
		User requester = userRepo.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		Chat chat = chatRepo.findById(chatId)
				.orElseThrow(() -> new EntityNotFoundException("Chat not found"));
		boolean isMember = 
				chat.getMembers().stream().anyMatch(u -> u.getId().equals(requester.getId()));
		if (!isMember) {
			throw new AccessDeniedException("User is not a member of the chat");
		}
		return messageRepo.findByChatIdOrderByCreatedAtAsc(chatId, pageable)
				.map(this::mapToDto);
	}

	private MessageResponse mapToDto(Message m) {
		return new MessageResponse(
				m.getId(), 
				m.getChat().getId(), 
				m.getSender().getId(), 
				m.getSender().getUsername(), 
				m.getContent(), 
				m.getCreatedAt()
		);
	}
	
}
