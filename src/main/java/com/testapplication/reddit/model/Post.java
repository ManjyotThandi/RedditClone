package com.testapplication.reddit.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long postId; 
	
	@NotNull (message = "Please provide a post name")
	private String postName;
	
	@Nullable
	private String url;
	
	@Lob
	private String description;
	
	
	private Integer voteCount;
	
	
	
}
