package com.testapplication.reddit.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.testapplication.reddit.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// loads data from diff sources, in our case it is db, provides data to spring
	// We have created an implementation of this UserDetailsService interface thus
	// why we are able to inject it here
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public void configure(HttpSecurity httpSecurity) throws Exception {
		// since we are not storing our session in a cookie, rather using tokens we can
		// disable csrf attacks
		httpSecurity.csrf().disable().
		// allow /api/auth pattern to send requests to our backend. Any diferent
		// patterns should be authenticated
				authorizeRequests().antMatchers("/api/auth/**").permitAll().anyRequest().authenticated();
		
		// Spring will check for token before username and password authentiation
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	// create an authentication manager using spring authentication manager builder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		// This authentician manager (created by the builder), reaches out to
		// userDetailsService spring (Loads data from diff sources)
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public KeyStore keyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance("JKS");

		// Getting the input stream from a keystore file (This file is loaded at run
		// time by class loader)
		InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");

		keyStore.load(resourceAsStream, "secret".toCharArray());
		return keyStore;
	}
}
