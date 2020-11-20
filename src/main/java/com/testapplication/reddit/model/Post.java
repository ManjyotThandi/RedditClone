package com.testapplication.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {

	private Long postId; 
	
	private String postName;
	
	private String url;
	
	private String description;
	
	private Integer voteCount;
	
	
	
}
