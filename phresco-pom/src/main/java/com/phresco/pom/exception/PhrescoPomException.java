package com.phresco.pom.exception;

import com.phresco.pom.util.POMErrorCode;

/**
 * @author suresh_ma
 *
 */
public class PhrescoPomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private POMErrorCode errorCode;

	/**
	 * 
	 */
	public PhrescoPomException() {
	}

	/**
	 * 
	 */
	public PhrescoPomException(POMErrorCode code) {
		this.errorCode = code;
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public PhrescoPomException(POMErrorCode code, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = code;
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public PhrescoPomException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public PhrescoPomException(POMErrorCode code, String message) {
		super(message);
		this.errorCode = code;
	}
	
	/**
	 * @param cause
	 */
	public PhrescoPomException(Throwable cause) {
		super(cause);
	}

	public POMErrorCode getErrorCode() {
		return errorCode;
	}
	
}