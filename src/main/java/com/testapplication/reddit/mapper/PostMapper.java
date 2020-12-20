package com.testapplication.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.testapplication.reddit.dto.PostRequest;
import com.testapplication.reddit.dto.PostResponse;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.model.User;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "description", source = "postRequest.description")
	Post map(PostRequest postRequest, Subreddit subreddit, User user);

	@Mapping(target = "id", source = "postId")
	@Mapping(target = "postName", source = "postName")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "url", source = "url")
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.userName")
	PostResponse mapToDto(Post post);
}
