package com.testapplication.reddit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.dto.VoteDTO;
import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.model.Post;
import com.testapplication.reddit.model.Vote;
import com.testapplication.reddit.model.VoteType;
import com.testapplication.reddit.repository.PostRepository;
import com.testapplication.reddit.repository.VoteRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class VoteService {

	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private AuthService authService;

	@Transactional
	public void vote(VoteDTO voteDto) {
		// vote type in voteDto is an enum value

		// Get the post using post Id in vote DTO
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> {
			throw new SpringRedditException(
					"Post not found with the ID" + voteDto.getPostId() + "which was provided in the vote");
		});

		// retrieve the most recent vote submitted by the current user for the given
		// post. It is okay for this to be optional

		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());

		// check if voteByPostAndUser is present and check if the voteType that is
		// already in the db matches the one that the user is submiting now

		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already voted for this post!" + voteDto.getVoteType());
		}

		if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}

		// Save the vote in the vote db so this user can't vote the same thing again for
		// this post (code above checks that)
		Vote vote = voteRepository.save(mapToVote(voteDto, post));

		// save the update post back to the post repository
		postRepository.save(post);

	}

	// choose not to use mapstruct here, not that many fields(?)
	private Vote mapToVote(VoteDTO voteDTO, Post post) {
		return Vote.builder().voteType(voteDTO.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}
}
