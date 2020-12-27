package com.testapplication.reddit.controller;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapplication.reddit.dto.AuthenticationResponse;
import com.testapplication.reddit.dto.LoginRequest;
import com.testapplication.reddit.dto.RegisterRequest;
import com.testapplication.reddit.service.AuthService;

import io.jsonwebtoken.security.InvalidKeyException;

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
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<String>("User Registered", HttpStatus.OK);
	}

	// This is sent from the email link we sent out to the user
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authService.verifyAccount(token);
		return new ResponseEntity<>("Message account activated successfully", HttpStatus.OK);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

}
