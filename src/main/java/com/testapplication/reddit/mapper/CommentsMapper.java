package com.testapplication.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.testapplication.reddit.dto.CommentsDTO;
import com.testapplication.reddit.model.Comment;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.User;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

	// id will be auto generated when saving comment object to db
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	Comment map(CommentsDTO commentsDto, Post post, User user);

	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDTO mapToDto(Comment comment);
}
