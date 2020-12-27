package com.testapplication.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findAllByUser(User user);
}
