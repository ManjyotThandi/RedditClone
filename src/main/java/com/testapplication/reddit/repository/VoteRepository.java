package com.testapplication.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.User;
import com.testapplication.reddit.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
} 
