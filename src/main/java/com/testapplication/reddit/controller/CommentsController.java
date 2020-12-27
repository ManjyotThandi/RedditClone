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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testapplication.reddit.dto.CommentsDTO;
import com.testapplication.reddit.service.CommentsService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@NoArgsConstructor
public class CommentsController {

	@Autowired
	private CommentsService commentsService;

	@PostMapping
	public ResponseEntity<Object> createComment(@RequestBody CommentsDTO commentsDTO) {

		commentsService.save(commentsDTO);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<CommentsDTO>> getAllCommentsForPost(@RequestParam("postId") Long postId) {

		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsForPost(postId));
	}
	
	@GetMapping("/User")
	public ResponseEntity<List<CommentsDTO>> getAllCommentsByUser(@RequestParam("userName") String userName){
		
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsByUser(userName));
	}
}
