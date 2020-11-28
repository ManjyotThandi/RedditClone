package com.testapplication.reddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapplication.reddit.dto.RegisterRequest;
import com.testapplication.reddit.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// if there is an error it will be thrown in MailService.java, else it will be
	// successful
	@PostMapping("/signup")
	public void signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
	}

}
