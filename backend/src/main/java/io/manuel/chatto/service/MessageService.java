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
import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService {
	
	private final MessageRepository messageRepo;
	private final ChatRepository chatRepo;
	
	public MessageService(MessageRepository messageRepo, ChatRepository chatRepo) {
		this.messageRepo = messageRepo;
		this.chatRepo = chatRepo;
	}
	
	@Transactional
	public MessageResponse sendMessage(Long chatId, User sender, String content) {
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
	public Page<MessageResponse> getMessages(Long chatId, Pageable pageable, User requester) {
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
