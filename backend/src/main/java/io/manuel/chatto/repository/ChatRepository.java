package io.manuel.chatto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.manuel.chatto.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

}
