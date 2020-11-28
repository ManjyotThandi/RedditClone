package com.testapplication.reddit.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RegisterRequest {

	private PasswordEncoder passwordEncoder;

	private String email;

	private String username;

	private String password;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@Autowired
	public void setPassword(PasswordEncoder passwordEncoder, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		this.password = encodedPassword;
	}

}
