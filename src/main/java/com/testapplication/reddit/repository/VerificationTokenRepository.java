package com.testapplication.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapplication.reddit.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);
}
