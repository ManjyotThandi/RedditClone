package com.testapplication.reddit.service;

import java.time.Instant;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.dto.RegisterRequest;
import com.testapplication.reddit.model.User;
import com.testapplication.reddit.model.VerificationToken;
import com.testapplication.reddit.repository.UserRepository;
import com.testapplication.reddit.repository.VerificationTokenRepository;

@Service
public class AuthService {

	private final ModelMapper modelMapper;

	private final UserRepository userRepository;

	private final VerificationTokenRepository verificationTokenRepository;

	@Autowired
	public AuthService(ModelMapper modelMapper, UserRepository userRepository,
			VerificationTokenRepository verificationTokenRepository) {
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
	}

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		// since we are using a dto we cant just simply save to db as it is not a user
		// object yet.
		// We need to map the dto to user object. We can manually do it by using the
		// setters on User but I would rather use Model Mapper to POJO to POJO mapping
		User user = convertRegisterRequestToUser(registerRequest);
		userRepository.save(user);

		String token = generateVerificationToken(user);

	}

	private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		User user = modelMapper.map(registerRequest, User.class);
		user.setCreated(Instant.now());
		user.setEnabled(false);

		return user;
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);

		return token;
	}
}
