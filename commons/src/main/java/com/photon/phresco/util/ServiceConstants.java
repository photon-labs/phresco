/*
 * ###
 * Phresco Service
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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.util;

public interface ServiceConstants {

	String REST_API_PROJECT =  "/project";
	String REST_API_COMPONENT =  "/components";
	String REST_API_ADMIN =  "/admin";
	String REST_API_CUSTOMERS = "/customers";
	String REST_API_APPTYPES = "/apptypes";
	String REST_API_CONFIG_TEMPLATES= "/configtemplates";
	String REST_API_MODULES= "/modules";
	String REST_API_PILOTS = "/pilots";
	String REST_API_SERVERS = "/servers";
	String REST_API_DATABASES = "/databases";
	String REST_API_WEBSERVICES = "/webservices";
	String REST_API_TECHNOLOGIES= "/technologies";
	String REST_API_DOWNLOADS = "/downloads";
	String REST_API_GLOBALURL="/globalurl";
	String REST_API_VIDEOS = "/videos";
	String REST_API_USERS = "/users";
	String REST_API_ROLES = "/roles";
	String REST_API_PERMISSIONS = "/permissions";
	String REST_API_LDAP = "/settings/ldap";
	String REST_API_SETTINGS = "/settings";
	String REST_API_TWEETS = "/tweets";
	String REST_API_JSBYID = "/modules/js";
	String REST_API_PILOTSBYID = "/pilots/id";
	String REST_API_LOGIN = "/login";
	String REST_LOGIN_PATH = "/service/rest/api/login";
	String REST_API_LDAP_PARAM_ID = "ldap";
	String REST_API_PATH_ID = "/{id}";
	String REST_API_PATH_PARAM_ID = "id";
	String REST_API_QUERY_PARAM_SESSION_ID = "sessionId";
	String REST_API_QUERY_PARAM_LIMIT = "limit";
	String REST_API_QUERY_PARAM_OFFSET = "offset";
	String ERROR_MSG_UNSUPPORTED_OPERATION = "{0} operation is not allowed";
	String ERROR_MSG_NOT_FOUND = "Content Not Found";
	String ERROR_MSG_ID_NOT_EQUAL = "Given Id Not Equal";
	String REST_QUERY_TECHID = "techId";
	String REST_QUERY_PROJECTID = "projectId";
	String REST_QUERY_TYPE = "type";
	String REST_QUERY_TYPE_MODULE = "module";
	String REST_QUERY_TYPE_JS = "js";
	String REST_QUERY_CUSTOMERID = "customerId";
	String DEFAULT_CUSTOMER_NAME = "photon";
	String PROJECT_NAME = "name";
	
	 /*
     * Constants for MongoDB Collections
     */
	String CUSTOMERS_COLLECTION_NAME = "customers";
	String VIDEOS_COLLECTION_NAME = "videos";
	String USERS_COLLECTION_NAME = "users";
	String DOWNLOAD_COLLECTION_NAME = "downloads";
	String GLOBALURL_COLLECTION_NAME="globalurl";
	String MODULES_COLLECTION_NAME = "modules";
	String APPTYPES_COLLECTION_NAME = "apptypes";
	String PILOTS_COLLECTION_NAME = "pilots";
	String SERVERS_COLLECTION_NAME = "servers";
	String DATABASES_COLLECTION_NAME = "databases";
	String WEBSERVICES_COLLECTION_NAME = "webservices";
	String SETTINGS_COLLECTION_NAME = "settings";
	String TECHNOLOGIES_COLLECTION_NAME = "technologies";
	String USERDAO_COLLECTION_NAME = "userdaos";
	String ROLES_COLLECTION_NAME = "roles";
	String APPTYPESDAO_COLLECTION_NAME = "apptypedao";
	
	/*
     * Constants for Exception Message keys
     */
	String EX_PHEX00001 = "PHEX00001";
	String EX_PHEX00002 = "PHEX00002";
	String EX_PHEX00003 = "PHEX00003";
	String EX_PHEX00004 = "PHEX00004";
	String EX_PHEX00005 = "PHEX00005";
	String EX_PHEX00006 = "PHEX00006";
	String EX_PHEX00007 = "PHEX00007";
	
	/*
     * Constants for Operatins
     */
	String UPDATE = "Update";
	String INSERT = "Insert";
	String DELETE = "Delete";
	
	/*
	 * Constants for URL in ServiceBaseAction
	 */
	String COLON_DOUBLE_SLASH = "://";
	String COLON = ":";
	String SLASH_REST_SLASH_API = "/rest/api";
	
	/*
     * Constants for Fields
     */
	String REST_API_FIELD_TECH = "technologies";
	String REST_API_FIELD_APPID = "appTypeId";
	
	/*
     * Constants for Media Type
     */
	String MEDIATYPE_ZIP = "application/zip";
}