package com.testapplication.reddit.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testapplication.reddit.dto.RegisterRequest;
import com.testapplication.reddit.model.User;

@Service
public class AuthService {

	@Autowired
	private ModelMapper modelMapper;

	public void signup(RegisterRequest registerRequest) {
		// since we are using a dto we cant just simply save to db as it is not a user
		// object yet.
		// We need to map the dto to user object. We can manually do it by using the
		// setters on User but I would rather use Model Mapper to POJO to POJO mapping
		convertRegisterRequestToUser(registerRequest);
	}

	private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		User user = modelMapper.map(registerRequest, User.class);
		return user;
	}
}
