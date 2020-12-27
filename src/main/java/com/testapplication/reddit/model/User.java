package com.testapplication.reddit.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(generator = "sequence")
	private Long userId;

	@NotNull(message = "Please provide a username")
	private String userName;

	@NotNull(message = "Please provide a password")
	private String password;

	@Email
	@NotNull(message = "Please provide an email address")
	private String email;

	private Instant created;

	private boolean enabled;
}
