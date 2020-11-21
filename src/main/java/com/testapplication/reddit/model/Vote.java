package com.testapplication.reddit.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long voteId;

	private VoteType voteType;

	@NotNull(message = "There needs to be a post in order to have a vote")
	private Post post;

	private User user;

}
