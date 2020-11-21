package com.testapplication.reddit.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subreddit {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long id;
	
	@NotNull(message = "Please provide a name for subreddit")
	private String name;
	
	@NotNull(message = "Description is required")
	private String description;
	
	private List<Post> posts;
	
	private Instant createdDate;
	
	private User user;
	
	
}
