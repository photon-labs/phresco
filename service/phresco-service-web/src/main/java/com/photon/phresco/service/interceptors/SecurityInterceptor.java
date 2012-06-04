package com.photon.phresco.service.interceptors;

import java.net.URI;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityInterceptor implements ContainerRequestFilter {

	private static final Logger LOGGER = Logger.getLogger(SecurityInterceptor.class); 
	
	public ContainerRequest filter(ContainerRequest request) {
		URI absolutePath = request.getAbsolutePath();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(absolutePath);
		}

		return request;
	}

}
