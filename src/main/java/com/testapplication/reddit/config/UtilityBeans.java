package com.testapplication.reddit.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilityBeans {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public KeyStore keyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance("JKS");

		// Getting the input stream from a keystore file (This file is loaded at run
		// time by class loader)
		InputStream resourceAsStream = getClass().getResourceAsStream("springblog.jks");

		keyStore.load(resourceAsStream, "secret".toCharArray());
		return keyStore;
	}

}
