package com.testapplication.reddit.model;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerificationToken {

	@Id
	@GeneratedValue(generator = "identity")
	private Long id;

	private String token;

	private User user;

	private Instant expiryDate;
}
