package com.photon.phresco.framework.api;

import java.io.Serializable;

public class ValidationResult implements Serializable {

	public ValidationResult() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Status {
		INFO, WARNING, ERROR
	};

	Status status;
	String message;

	public ValidationResult(Status status, String message) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ValidationResult [message=" + message + ", status=" + status
				+ "]";
	}

}

