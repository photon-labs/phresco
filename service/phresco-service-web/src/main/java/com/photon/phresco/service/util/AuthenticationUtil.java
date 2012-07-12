package com.photon.phresco.service.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.photon.phresco.exception.PhrescoException;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
public class AuthenticationUtil {
	
	private static final String ALGORITHM_NAME = "SHA1PRNG";
	private static final String AUTH_TOKEN_CACHE_TIME = "auth.token.cache.ttl";
	private static AuthenticationUtil authTokenUtil = null;
	Cache<String, String> tokenCache = null;

	public static AuthenticationUtil getInstance() throws PhrescoException {
		return getInstance(null);
	}
	
	public static AuthenticationUtil getInstance(InputStream is) throws PhrescoException {
		if (authTokenUtil == null) {
			authTokenUtil = new AuthenticationUtil(is);
		}
		return authTokenUtil;
	}
	
	private AuthenticationUtil(InputStream is) throws PhrescoException {
		try {
			Properties properties = new Properties();
			if (is == null) {
				is = this.getClass().getClassLoader().getResourceAsStream("server.config");
			}
			properties.load(is);
			String cacheTTL = properties.getProperty(AUTH_TOKEN_CACHE_TIME); 
			Long tokenIdleTime = Long.parseLong(cacheTTL);
			tokenCache = CacheBuilder.newBuilder().expireAfterAccess(tokenIdleTime, TimeUnit.MINUTES).removalListener(
					new RemovalListener() 
					{
						@Override
						public void onRemoval(RemovalNotification notification) {
							System.out.println("testing");
						}
					}).build();
		} catch (NumberFormatException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	public String generateToken(String userName) throws PhrescoException {
		String token = "";
		try {
			SecureRandom random = new SecureRandom();
			byte[] seed = random.generateSeed(132);
			random.setSeed(seed);
			token = new BigInteger(132, random).toString(32);
			tokenCache.put(token, userName);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return token;
	}
	
	public String getUserName(String token) {
		return tokenCache.getIfPresent(token);
	}

	public boolean isValidToken(String token) {
		boolean validToken = false;
		if (tokenCache.getIfPresent(token) != null) {
			validToken = true;
		}
		return validToken;
	}
}
