/*
 * ###
 * Service Web Archive
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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
package com.photon.phresco.service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;


public class UnauthorizedException extends WebApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
      * Create a HTTP 401 (Unauthorized) exception.
     */
     public UnauthorizedException() {
         super(Response.status(Status.UNAUTHORIZED).build());
     }

     /**
      * Create a HTTP 404 (Not Found) exception.
      * @param message the String that is the entity of the 404 response.
      */
     public UnauthorizedException(String message) {
         super(Response.status(Status.UNAUTHORIZED).entity(message).type("text/plain").build());
     }

}
