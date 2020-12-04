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
import org.springframework.stereotype.Service;

import com.testapplication.reddit.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class JwtProvider {

	private KeyStore keyStore;

	@Autowired
	public JwtProvider(KeyStore keyStore) {
		this.keyStore = keyStore;
	}

	public String generateToken(Authentication authentication)
			throws InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		User principal = (User) authentication.getPrincipal();

		return Jwts.builder().setSubject(principal.getUserName()).signWith(getPrivateKey()).compact();
	}

	private PrivateKey getPrivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
	}
}
