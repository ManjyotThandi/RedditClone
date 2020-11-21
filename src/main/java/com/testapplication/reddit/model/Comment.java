package com.testapplication.reddit.model;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long id;

	@NotNull(message = "Comment can't be empty")
	private String text;

	private Post post;

	private Instant createdDate;

	private User user;
}
