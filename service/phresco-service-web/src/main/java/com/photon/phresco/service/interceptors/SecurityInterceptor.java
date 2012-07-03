/*
 * ###
 * Service Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.service.interceptors;

import java.net.URI;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.util.AuthenticationUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityInterceptor implements ContainerRequestFilter {

	private static final Logger LOGGER = Logger.getLogger(SecurityInterceptor.class); 
	
	public ContainerRequest filter(ContainerRequest request) {
		URI absolutePath = request.getAbsolutePath();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(absolutePath);
		}
		
		if(absolutePath.getPath().equals(ServiceConstants.REST_LOGIN_PATH)) {
			return request;
		}
		
		String headerValue = request.getHeaderValue(Constants.PHR_AUTH_TOKEN);
		try {
			AuthenticationUtil authTokenUtil = AuthenticationUtil.getInstance();
			boolean isValid = authTokenUtil.isValidToken(headerValue);
			if(isValid) {
				return request;
			}
		} catch (Exception e) {
			try {
				throw new PhrescoException(ServiceConstants.EX_PHEX00007);
			} catch (PhrescoException e1) {
				e1.printStackTrace();
			}
		}
		 
		return null;
	}

}
