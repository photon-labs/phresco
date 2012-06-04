package com.photon.phresco.model;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogInfo implements Serializable {
	
	private String message;	
	private String trace;	
	private String action;
	private String userId;
//	private Object supportingInfo;

	public LogInfo() {

	}
	
	public LogInfo(String errorMessage, String trace,
			String action, String userid) {
		super();
		this.message = errorMessage;
		this.trace = trace;
		this.action = action;
		this.userId = userid;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return message;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.message = errorMessage;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the trace
	 */
	public String getTrace() {
		return trace;
	}

	/**
	 * @param trace the trace to set
	 */
	public void setTrace(String trace) {
		this.trace = trace;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LogInfo [message=" + message + ", trace=" + trace + ", action="
				+ action + ", userId=" + userId + "]";
	}

}
