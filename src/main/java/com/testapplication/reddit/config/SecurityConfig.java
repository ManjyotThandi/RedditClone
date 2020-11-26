package com.testapplication.reddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public void configure(HttpSecurity httpSecurity) throws Exception {
		// since we are not storing our session in a cookie, rather using tokens we can
		// disable csrf attacks
		httpSecurity.csrf().disable().
		// allow /api/auth pattern to send requests to our backend. Any diferent
		// patterns should be authenticated
				authorizeRequests().antMatchers("/api/auth/**")
				.permitAll().anyRequest().authenticated();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
