package com.testapplication.reddit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.dto.SubredditDTO;
import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.mapper.SubredditMapper;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.repository.SubRedditRepository;

@Service
public class SubredditService {

	@Autowired
	private SubRedditRepository subredditRepository;
	@Autowired
	private SubredditMapper subredditMapper;

	@Transactional
	public SubredditDTO save(SubredditDTO subredditDTO) {
		// use mapstruct to map from dto to actual object
		Subreddit subreddit = subredditMapper.mapSubredditDTOtoSubreddit(subredditDTO);

		Subreddit savedSubreddit = subredditRepository.save(subreddit);

		// This will return the subreddit saved so using that build the DTO back up
		// again and send it back to user
		//subredditDTO.setId(savedSubreddit.getId());
		return subredditDTO;
	}

	@Transactional
	public List<SubredditDTO> getAll() {
		// use mapstruct to map frop subreddit to dto, and send that back to user
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
	}

	public SubredditDTO getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> {
			throw new SpringRedditException("Can't find subreddit with id" + id);
		});

		return subredditMapper.mapSubredditToDto(subreddit);
	}

}
