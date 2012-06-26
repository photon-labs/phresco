package com.photon.phresco.exception;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PhrescoWebServiceException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Response response;
	private static InputStream is = null;
	private static Properties exceptionProp = null;
	
	static {
		is = PhrescoWebServiceException.class.getClassLoader().getResourceAsStream("exception-messages.properties");
		exceptionProp = new Properties();
		try {
			exceptionProp.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PhrescoWebServiceException(String errorNum, String type) {
        this(null, errorNum, type);        
    }
	
	public PhrescoWebServiceException(Throwable cause, String errorNum, String type) {
        super(cause);
        System.out.println("************************************");
        System.out.println(MessageFormat.format(exceptionProp.getProperty(errorNum), type ));
        System.out.println("************************************");
        this.response = Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(MessageFormat.format(exceptionProp.getProperty(errorNum), type )).build();
    }
}
