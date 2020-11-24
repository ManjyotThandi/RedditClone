package com.testapplication.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapplication.reddit.model.Comment;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);
	
	List<Comment> findAllByUser(User user);
} 
