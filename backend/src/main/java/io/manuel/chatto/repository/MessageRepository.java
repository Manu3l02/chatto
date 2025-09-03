package io.manuel.chatto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.manuel.chatto.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
