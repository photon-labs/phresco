package com.phresco.pom.util;

public class POMErrorCode {
	
	private int code;
	
	private String message;
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param code
	 * @param message
	 */
	private POMErrorCode(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public static POMErrorCode DEPENDENCY_NOT_FOUND = new POMErrorCode(101, "DEPENDENCY NOT AVAILABLE"); 

	public static POMErrorCode MODULE_NOT_FOUND = new POMErrorCode(102, "MODULE NOT AVAILABLE");
	
	public static POMErrorCode BUILD_NOT_FOUND = new POMErrorCode(103, "BUILD NOT AVAILABLE");
	
	public static POMErrorCode PLUGIN_NOT_FOUND = new POMErrorCode(104, "PLUGIN NOT AVAILABLE");
	
	public static POMErrorCode PROPERTY_NOT_FOUND = new POMErrorCode(105, "PROPERTY NOT AVAILABLE");
	
	public static POMErrorCode SOURCE_DIRECTORY_NOT_FOUND = new POMErrorCode(106, "SOURCE DIRECTORY NOT AVAILABLE");
	
	public static POMErrorCode PROFILE_NOT_FOUND = new POMErrorCode(107, "PROFILE NOT AVAILABLE");
	
	public static POMErrorCode KEYSTORE_NOT_FOUND = new POMErrorCode(108, "KEYSTORE VALUE NOT AVAILABLE");
	
	public static POMErrorCode PROFILE_ID_NOT_FOUND = new POMErrorCode(109, "PROFILE ID NOT AVAILABLE");
	
	public static POMErrorCode MODULE_EXIST = new POMErrorCode(110, "MODULE ALREADY EXIST");
	
	public static POMErrorCode PROFILE_ID_EXIST = new POMErrorCode(110, "PROFILE ID ALREADY EXIST");
}
