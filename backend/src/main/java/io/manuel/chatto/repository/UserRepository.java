package io.manuel.chatto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.manuel.chatto.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
