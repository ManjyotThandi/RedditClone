package com.testapplication.reddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<void> createPost(@RequestBody PostRequestDTO postDTO){
		postService.save(postDTO);
	}
}
