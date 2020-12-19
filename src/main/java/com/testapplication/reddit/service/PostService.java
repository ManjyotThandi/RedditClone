package com.testapplication.reddit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.dto.PostRequest;
import com.testapplication.reddit.dto.PostResponse;
import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.mapper.PostMapper;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.repository.PostRepository;
import com.testapplication.reddit.repository.SubRedditRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private SubRedditRepository subredditRepository;

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> {
			throw new SpringRedditException("Can't find the post with id" + id);
		});

		// using mapstruct to map from post to postdto (post response in our case)
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {

		List<Post> posts = postRepository.findAll();

		// convert these to postresponse dtos and return those to the user

		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());

	}

	public void save(PostRequest postRequest) {
		// In a post request we only get the required details as strings (subreddit name
		// etc). We need to get the required details first as an object
		// get the subreddit in which this post will be store
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> {
			throw new SpringRedditException("Subreddit not found" + postRequest.getSubredditName());
		});
	}

}
