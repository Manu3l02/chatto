package io.manuel.chatto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.manuel.chatto.model.Chat;
import io.manuel.chatto.model.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
	
	List<Chat> findByMembersContaining(User user);
}
