package com.testapplication.reddit.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long id;

	@NotNull(message = "Comment can't be empty")
	private String text;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	private Post post;

	private Instant createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
}
