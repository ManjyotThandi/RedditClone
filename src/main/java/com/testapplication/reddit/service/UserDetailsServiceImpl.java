package com.testapplication.reddit.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapplication.reddit.exceptions.UserNameNotFoundException;
import com.testapplication.reddit.model.User;
import com.testapplication.reddit.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {

		Optional<User> userOptional = userRepository.findByuserName(username);

		User user = userOptional.orElseThrow(() -> new UserNameNotFoundException("Username not found" + username));

		// from the user we got from our repository send it to the spring method User
		// This is like a wrapper object with the same name User.
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				user.isEnabled(), true, true, true, getAuthorities("USER"));

	}

	// singletonList returns a immutable List with only one object
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

}
