package com.testapplication.reddit.model;

public enum VoteType {
	UPVOTE(1), // calls constructor with value 1
	DOWNVOTE(-1) // calls constructor with value -1
	;

	private final int direction;

	VoteType(int direction) {
		this.direction = direction;
	}

}
