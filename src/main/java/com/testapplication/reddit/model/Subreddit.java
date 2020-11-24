package com.testapplication.reddit.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subreddit {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long id;
	
	@NotNull(message = "Please provide a name for subreddit")
	private String name;
	
	@NotNull(message = "Description is required")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	private List<Post> posts;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	
}
