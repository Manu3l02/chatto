package io.manuel.chatto.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "is_group", nullable = false)
	private boolean isGroup = false;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToMany
	@JoinTable(
		name = "chat_members",
		joinColumns = @JoinColumn(name = "chat_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> members = new HashSet<User>();
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
