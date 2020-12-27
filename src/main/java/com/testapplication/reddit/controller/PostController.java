package com.testapplication.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapplication.reddit.dto.PostRequest;
import com.testapplication.reddit.dto.PostResponse;
import com.testapplication.reddit.service.PostService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
@NoArgsConstructor
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postDTO) {
		postService.save(postDTO);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());

	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}

	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostBySubredditId(@PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}

	@GetMapping("by-username/{userName)")
	public ResponseEntity<List<PostResponse>> getPostByUserName(@PathVariable String userName) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(userName));
	}
}
