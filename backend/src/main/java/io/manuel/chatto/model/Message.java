package io.manuel.chatto.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
