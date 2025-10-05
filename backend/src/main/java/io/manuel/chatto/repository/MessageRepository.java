package io.manuel.chatto.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.manuel.chatto.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	Page<Message> findByChatIdOrderByCreatedAtAsc(Long chatId, Pageable pageable);
	List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
