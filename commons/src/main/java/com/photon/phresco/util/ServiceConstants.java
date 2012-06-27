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
    /*
     * Constants for Archetype parameters
     */
	String ARCHETYPE_ARCHETYPEGROUPID = "-DarchetypeGroupId";
    String ARCHETYPE_ARCHETYPEARTIFACTID = "-DarchetypeArtifactId";
    String ARCHETYPE_ARCHETYPEVERSION = "-DarchetypeVersion";
    String ARCHETYPE_GROUPID = "-DgroupId";
    String ARCHETYPE_ARTIFACTID = "-DartifactId";
    String ARCHETYPE_VERSION = "-Dversion";
    String ARCHETYPE_NAME = "-Dname";
    String ARCHETYPE_ARCHETYPEREPOSITORYURL = "-DarchetypeRepository";
    String ARCHETYPE_INTERACTIVEMODE = "-DinteractiveMode";

    /*
     * Constants for Phresco Archetypes
     */
    String PHRESCO_PHP_ARCHETYPE = "phresco-php-archetype";
    String PHRESCO_DRUPAL7_ARCHETYPE = "phresco-drupal7-archetype";
    String PHRESCO_NODEJS_ARCHETYPE = "phresco-nodejs-archetype";
    String PHRESCO_SHAREPOINT_ARCHETYPE = "phresco-sharepoint-archetype";
    String PHRESCO_ANDROID_ARCHETYPE = "phresco-android-archetype";
    String PHRESCO_IPHONE_ARCHETYPE = "phresco-iphone-archetype";

    /*
     * Constants for Technology
     * these values must be synchronous with the client values
     * if required move these constants to phresco commons constants
     */
    String TECHNOLOGY_PHP = "PHP";
    String TECHNOLOGY_PHP_DRUPAL = "PHP_DRUPAL";
    String TECHNOLOGY_NODE_JS = "NODE_JS";
    String TECHNOLOGY_SHAREPOINT = "SHAREPOINT";
    String TECHNOLOGY_ANDROID = "ANDROID";
    String TECHNOLOGY_IPHONE = "IPHONE";

    /*
     * Constants for String
     */
    String STR_BLANK_SPACE = " ";
    String STR_EQUALS = "=";
    String STR_COMMA = ",";
    String CMD_ARG_VAR = "-D";
    String STR_UNDER_SCORE="_";

    /*
     *  Constants for System properties
     */
    String USER_HOME_DIR = "user.home";
    String OS_NAME = "os.name";

    //TODO:Move this to dependency properties
    String SETTINGS_CONFIG_FILE = "/config/settings/0.1/settings-0.1.json";
    String HOMEPAGE_JSON_FILE	= "/videos/homepage/videoinfo/1.0/videoinfo-1.0.json";
    String ADMIN_CONFIG_FILE = "/config/admin/0.1/admin-0.1.json";

    /*
     * Constants for LDAP properties
     */
    String LDAP_CONTEXT_FACTORY = "phresco.ldap.contextfactory";
    String LDAP_URL = "phresco.ldap.url";
    String LDAP_BASEDN = "phresco.ldap.basedn";
    String LDAP_LOGIN_ATTRIBUTE = "phresco.ldap.login.attribute";
    String LDAP_DISPLAY_NAME_ATTRIBUTE = "phresco.ldap.attribute.displayName";
    String LDAP_MAIL_ATTRIBUTE = "phresco.ldap.attribute.mail";
    String LDAP_CUSTOMER_NAME="phresco.ldap.attribute.customerName";
    String LDAP_PHRESCO_ENABLED="phresco.ldap.attribute.phrescoEnabled";
  

    /*
     * Constants for Version
     */
    String FRAMEWORK_VERSION_GROUPID = "com.photon.phresco.framework";
    String FRAMEWORK_VERSION_ARTIFACTID = "phresco-framework-web";
	String SERVICE_VERSION_GROUPID		=	"com.photon.phresco.service";
    String SERVICE_VERSION_ARTIFACTID = "phresco-service-web";

    ///Keys for configuration to load from config file
    String CONFIG_KEY_REPOSITORY_USERNAME = "repository.username";
    String CONFIG_KEY_REPOSITORY_PASSWORD = "repository.password";
	String CONFIG_KEY_REPOSITORY_PROTOCOL = "repository.protocol";
	String CONFIG_KEY_REPOSITORY_HOST = "repository.host";
    String CONFIG_KEY_REPOSITORY_PORT = "repository.port";
	String CONFIG_KEY_REPOSITORY_PATH = "repository.path";

	  //Authentication Constants
	 String AUTHENTICATE = "/authenticate";

	String REST_API_COMPONENT =  "/component";
	String REST_API_ADMIN =  "/admin-temp";
	String REST_API_CUSTOMERS = "/customers";
	String REST_API_APPTYPES = "/apptypes";
	String REST_API_CONFIG_TEMPLATES= "/configtemplates";
	String REST_API_MODULES= "/modules";
	String REST_API_PILOTS = "/pilot";
	String REST_API_SERVERS = "/servers";
	String REST_API_DATABASES = "/databases";
	String REST_API_WEBSERVICES = "/webservices";
	String REST_API_TECHNOLOGIES= "/technologies";
	String REST_API_DOWNLOADS = "/downloads-temp";
	String REST_API_VIDEOS = "/videos";
	String REST_API_USERS = "/users";
	String REST_API_ROLES = "/roles";
	String REST_API_PERMISSIONS = "/permissions";
	String REST_API_LDAP = "/settings/ldap";
	String REST_API_SETTINGS = "/settings_temp";
	String REST_API_TWEETS = "/tweets";
	
	String REST_API_LDAP_PARAM_ID = "ldap";
	
	


	String REST_API_PATH_ID = "/{id}";
	String REST_API_PATH_PARAM_ID = "id";
	String REST_API_QUERY_PARAM_SESSION_ID = "sessionId";
	String REST_API_QUERY_PARAM_LIMIT = "limit";
	String REST_API_QUERY_PARAM_OFFSET = "offset";

	String ERROR_MSG_UNSUPPORTED_OPERATION = "{0} operation is not allowed";
	String ERROR_MSG_NOT_FOUND = "Content Not Found";
	String ERROR_MSG_ID_NOT_EQUAL = "Given Id Not Equal";
	
	 /*
     * Constants for MongoDB Collections
     */

	String CUSTOMERS_COLLECTION_NAME = "customers";
	String VIDEOS_COLLECTION_NAME = "videos";
	String USERS_COLLECTION_NAME = "users";
	String DOWNLOAD_COLLECTION_NAME = "downloads";
	String MODULES_COLLECTION_NAME = "modules";
	String APPTYPES_COLLECTION_NAME = "apptypes";
	String PILOTS_COLLECTION_NAME = "pilots";
	String SERVERS_COLLECTION_NAME = "servers";
	String DATABASES_COLLECTION_NAME = "databases";
	String WEBSERVICES_COLLECTION_NAME = "webservices";
	String SETTINGS_COLLECTION_NAME = "settings";
	String TECHNOLOGIES_COLLECTION_NAME = "technologies";
	
	/*
     * Constants for Exception Message keys
     */
	String EX_PHEX00001 = "PHEX00001";
	String EX_PHEX00002 = "PHEX00002";
	String EX_PHEX00003 = "PHEX00003";
	String EX_PHEX00004 = "PHEX00004";
	String EX_PHEX00005 = "PHEX00005";
	String EX_PHEX00006 = "PHEX00006";
	
	/*
     * Constants for Operatins
     */
	
	String UPDATE = "Update";
	String INSERT = "Insert";
	String DELETE = "Delete";
}
