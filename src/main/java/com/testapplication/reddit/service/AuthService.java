package com.testapplication.reddit.service;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.dto.AuthenticationResponse;
import com.testapplication.reddit.dto.LoginRequest;
import com.testapplication.reddit.dto.RegisterRequest;
import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.model.NotificationEmail;
import com.testapplication.reddit.model.User;
import com.testapplication.reddit.model.VerificationToken;
import com.testapplication.reddit.repository.UserRepository;
import com.testapplication.reddit.repository.VerificationTokenRepository;
import com.testapplication.reddit.security.JwtProvider;

import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class AuthService {

	private final ModelMapper modelMapper;

	private final UserRepository userRepository;

	private final VerificationTokenRepository verificationTokenRepository;

	private final MailService mailService;

	private final PasswordEncoder passwordEncoder;

	// This is getting injected from securityconfig
	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;

	@Autowired
	public AuthService(ModelMapper modelMapper, UserRepository userRepository,
			VerificationTokenRepository verificationTokenRepository, MailService mailService,
			PasswordEncoder passwordEncoder,
			@Qualifier("org.springframework.security.authenticationManager") AuthenticationManager authenticationManager,
			JwtProvider jwtProvider) {
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
	}

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		// since we are using a dto we cant just simply save to db as it is not a user
		// object yet.
		// We need to map the dto to user object. We can manually do it by using the
		// setters on User but I would rather use Model Mapper for POJO to POJO mapping
		User user = convertRegisterRequestToUser(registerRequest);
		userRepository.save(user);

		String token = generateVerificationToken(user);

		// This sends the email. We will send them a link to our backend so that once
		// clicked it will look up their token
		// get the user associated with that token and then enable it
		mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(),
				"Thank you for signing up, please click on the link" + "localhost8080/api/auth/accountVerification/"
						+ token));
	}

	private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
		// put the bcrypt logic inside the dto class before we even get here password is
		// now encrypted when jackson maps to request body in controller
		// M.T bcrypt logic did not work in dto class.. putting it back here.
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		User user = modelMapper.map(registerRequest, User.class);
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
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

	public void verifyAccount(String token) {
		// query verification token repository to get token
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new SpringRedditException("InvalidToken"));

		fetchUserAndEnable(verificationToken);
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String userName = verificationToken.getUser().getUserName();

		User user = userRepository.findByuserName(userName).orElseThrow(
				() -> new SpringRedditException("User not found for this verification token. Username is" + userName));

		user.setEnabled(true);

		// once we have enabled the user, save them back to db so when client requests
		// we can verify from db
		userRepository.save(user);

	}

	public AuthenticationResponse login(LoginRequest loginRequest){
		// implement logic to authenticate user
		//Somewhere here the usersdetailserviceimpl is used as part of authenication manager and the user is authenticated
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		
		// returns an object of Authentication. This will set authenticated to true (?)
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// create the token
		String token = jwtProvider.generateToken(authentication);

		// We would rather send this dto back to user
		return new AuthenticationResponse(token, loginRequest.getUserName());

	}
}
