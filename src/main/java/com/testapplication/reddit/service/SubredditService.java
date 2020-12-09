package com.testapplication.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testapplication.reddit.dto.SubredditDTO;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.repository.SubRedditRepository;

@Service
public class SubredditService {

	@Autowired
	private SubRedditRepository subredditRepository;

	public SubredditDTO save(SubredditDTO subredditDTO) {
		// use lombok builder pattern
		Subreddit subreddit = Subreddit.builder().name(subredditDTO.getSubredditName())
				.description(subredditDTO.getDescription()).build();

		Subreddit savedSubreddit = subredditRepository.save(subreddit);

		// This will return the subreddit saved so using that build the DTO back up
		// again and send it back to user
		subredditDTO.setId(savedSubreddit.getId());
		return subredditDTO;
	}

	public List<SubredditDTO> getAll() {
		// take the subreddit and map it to dto and return that back to user
		return subredditRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	private SubredditDTO mapToDTO(Subreddit subreddit) {
		return SubredditDTO.builder().subredditName(subreddit.getName()).id(subreddit.getId())
				.numberOfPosts(subreddit.getPosts().size()).build();
	}
}
