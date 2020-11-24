package com.testapplication.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapplication.reddit.model.Subreddit;

public interface SubRedditRepository extends JpaRepository<Subreddit, Long> {

	Optional<Subreddit> findByName(String subRedditName);
} 

