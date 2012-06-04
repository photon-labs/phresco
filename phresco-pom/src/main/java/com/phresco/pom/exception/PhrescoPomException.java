/*
 * ###
 * phresco-pom
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