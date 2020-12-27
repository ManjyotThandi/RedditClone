package com.testapplication.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapplication.reddit.dto.SubredditDTO;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubRedditController {

	@Autowired
	private SubredditService subredditService;

	@PostMapping
	public ResponseEntity<SubredditDTO> createSubreddit(@RequestBody SubredditDTO subredditDTO) {
		SubredditDTO subreddit = subredditService.save(subredditDTO);
		return ResponseEntity.status(201).body(subreddit);
	}

	@GetMapping
	public ResponseEntity<List<SubredditDTO>> getAllSubreddits() {
		return ResponseEntity.status(201).body(subredditService.getAll());
	}

}
