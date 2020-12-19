package com.testapplication.reddit.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.testapplication.reddit.dto.SubredditDTO;
import com.testapplication.reddit.model.Subreddit;

// componentModel = spring is used so spring can inject this mapstruct mapper wherever we need to use it
@Mapper(componentModel = "spring")
public interface SubredditMapper {

	// map subreddt to subreddit DTO
	@Mapping(target = "numberOfPosts", expression = "java(getNumberOfPosts(subreddit))")
	SubredditDTO mapSubredditToDto(Subreddit subreddit);

	// perform inverse
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapSubredditDTOtoSubreddit(SubredditDTO subredditDTO);

	default Integer getNumberOfPosts(Subreddit subreddit) {
		int posts = subreddit.getPosts().size();

		return posts;
	}

}
