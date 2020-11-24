package com.testapplication.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapplication.reddit.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
}
