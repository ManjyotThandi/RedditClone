package com.testapplication.reddit.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class JwtProvider {

	private KeyStore keyStore;

	@Autowired
	public JwtProvider(KeyStore keyStore) {
		this.keyStore = keyStore;
	}

	public String generateToken(Authentication authentication) {
		
		
		// essentially getting principal being authenticated from the  Authentication object and down casting it to a Spring User
		User principal = (User) authentication.getPrincipal();

		try {
			// creating a token here and setting the subject to the username, signing it with private key
			// private key comes from keystore
			return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
		} catch (InvalidKeyException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private PrivateKey getPrivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
	}
}
