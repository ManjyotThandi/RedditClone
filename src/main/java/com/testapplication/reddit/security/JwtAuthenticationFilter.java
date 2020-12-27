package com.testapplication.reddit.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserDetailsService userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = getJwtFromRequest(request);

		if (jwt != null && !jwt.trim().isEmpty() && jwtProvider.validateToken(jwt)) {

			String username = jwtProvider.getUsernameFromJwt(jwt);

			// retrieve user from database using spring UserDetailsService implementation we
			// provided
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// Send the request to the next filter if applicable
		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");

		// check if the Authorization header has the word Bearer in it
		if (bearerToken != null && !bearerToken.trim().isEmpty() && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return bearerToken;
	}

}
