package com.testapplication.reddit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testapplication.reddit.dto.CommentsDTO;
import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.mapper.CommentsMapper;
import com.testapplication.reddit.model.Comment;
import com.testapplication.reddit.model.NotificationEmail;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.User;
import com.testapplication.reddit.repository.CommentRepository;
import com.testapplication.reddit.repository.PostRepository;
import com.testapplication.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CommentsService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private CommentsMapper commentsMapper;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private MailContentBuilder mailContentBuilder;
	@Autowired
	private MailService mailService;

	private static final String POST_URL = "";

	public void save(CommentsDTO commentsDTO) {
		// we need to get post and user from commentsDTO
		Post post = postRepository.findById(commentsDTO.getPostId()).orElseThrow(() -> {
			throw new SpringRedditException("Post not found with the id " + commentsDTO.getPostId());
		});

		// instead of using the userrepository to get the user, we can call
		// getCurrentUser in the AuthService class
		// convert commentsDTO to comment using mapstruct
		Comment comment = commentsMapper.map(commentsDTO, post, authService.getCurrentUser());

		// save comment to db
		commentRepository.save(comment);

		String message = mailContentBuilder
				.build(post.getUser().getUserName() + " posted a comment on your post" + POST_URL);

		sendCommentNotification(message, post.getUser());
	}

	public List<CommentsDTO> getAllCommentsForPost(Long postId) {

		Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new SpringRedditException("Post not found with the id " + postId);
		});

		List<Comment> comments = commentRepository.findByPost(post);

		return comments.stream().map(commentsMapper::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDTO> getAllCommentsByUser(String userName) {

		User user = userRepository.findByuserName(userName).orElseThrow(() -> {
			throw new SpringRedditException("User not found with the username " + userName);
		});

		List<Comment> comments = commentRepository.findAllByUser(user);

		return comments.stream().map(commentsMapper::mapToDto).collect(Collectors.toList());

	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUserName() + "Commented on your post", user.getEmail(), message));
	}
	

}
