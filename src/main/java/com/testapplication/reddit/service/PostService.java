package com.testapplication.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.dto.PostRequest;
import com.testapplication.reddit.dto.PostResponse;
import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.mapper.PostMapper;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.Subreddit;
import com.testapplication.reddit.model.User;
import com.testapplication.reddit.repository.PostRepository;
import com.testapplication.reddit.repository.SubRedditRepository;
import com.testapplication.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

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

	@Transactional
	public void save(PostRequest postRequest) {
		// In a post request we only get the required details as strings (subreddit name
		// etc). We need to get the required details first as an object
		// get the subreddit in which this post will be stored
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> {
			throw new SpringRedditException("Subreddit not found" + postRequest.getSubredditName());
		});

		// now we can map this postrequest to a post

		// TODO now put postrequestdto into post using post, subreddit from above, and
		// user from getcurrentuser in authservice. This provides the current user from
		// the thread
		Post post = postMapper.map(postRequest, subreddit, authService.getCurrentUser());

		postRepository.save(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> {
			throw new SpringRedditException("No subreddit found with the id" + subredditId);
		});

		List<Post> post = postRepository.findAllBySubreddit(subreddit);

		return post.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String userName) {
		User user = userRepository.findByuserName(userName).orElseThrow(() -> {
			throw new SpringRedditException("User not found with the username" + userName);
		});

		List<Post> post = postRepository.findAllByUser(user);

		return post.parallelStream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

}
