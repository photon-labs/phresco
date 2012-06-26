package com.photon.phresco.service.util;

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
