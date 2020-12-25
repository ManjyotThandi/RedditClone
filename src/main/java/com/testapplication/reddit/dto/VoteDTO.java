package com.testapplication.reddit.dto;

import com.testapplication.reddit.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteDTO {

	private VoteType voteType;
	
	private Long postId;
}
