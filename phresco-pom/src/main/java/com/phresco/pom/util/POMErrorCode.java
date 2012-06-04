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
