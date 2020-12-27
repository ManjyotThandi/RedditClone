package com.testapplication.reddit.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Vote {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long voteId;

	private VoteType voteType;

	@NotNull(message = "There needs to be a post in order to have a vote")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	private User user;

}
