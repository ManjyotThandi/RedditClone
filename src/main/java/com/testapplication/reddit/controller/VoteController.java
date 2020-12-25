package com.testapplication.reddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapplication.reddit.dto.VoteDTO;
import com.testapplication.reddit.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	@Autowired
	private VoteService voteService;

	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDTO) {
		voteService.vote(voteDTO);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
