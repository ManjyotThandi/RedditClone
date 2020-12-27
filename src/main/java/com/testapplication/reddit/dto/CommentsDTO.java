package com.testapplication.reddit.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentsDTO {
	private Long id;
	private Long postId;
	private Instant createdDate;
	private String text;
	private String userName;
}
