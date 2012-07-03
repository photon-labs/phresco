/*
 * ###
 * Service Web Archive
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
package com.photon.phresco.service.admin.commons;

public interface ServiceUIConstants {

	/*****************************
     * Request Constants
     * String REQ_LOGIN_XXX
     *****************************/	
    String REQ_LOGIN_ERROR	= "loginError";
    String REQ_FROM_PAGE	= "fromPage";
    String REQ_USER_INFO = "userInfo";
    String ERROR_LOGIN = "err.login.invalid.cred";
    String ERROR_LOGIN_ACCESS_DENIED = "err.login.access.denied";


    
	/*****************************
     * Common Constants
     *****************************/	
    String SQUARE_CLOSE = "]";
    String COMMA = ",";
    String SQUARE_OPEN = "[";
    String ADD = "add";
    
    
    

	/*****************************
     * I18N Keys Constants
     * String KEY_I18N_XXX_YYY
     *****************************/	

    String KEY_I18N_LOGIN_INVALID_CRED		= "err.login.invalid.cred";
    String KEY_I18N_LOGIN_EMPTY_CRED		= "err.login.empty.cred";
    String KEY_I18N_LOGIN_ACCESS_DENIED		= "err.login.invalid.cred";
    

    String KEY_I18N_ERR_NAME_EMPTY = "err.msg.name.empty";
    String KEY_I18N_ERR_DESC_EMPTY = "err.msg.desc.empty";
    String KEY_I18N_ERR_VER_EMPTY = "err.msg.ver.empty";
    String KEY_I18N_ERR_FILE_EMPTY	= "err.msg.file.empty";
    
    String KEY_I18N_ERR_APPTYPE_EMPTY = "err.msg.apptye.empty";
    String KEY_I18N_ERR_APPLIES_EMPTY = "err.msg.applies.empty";
    String KEY_I18N_ERR_APPLNJAR_EMPTY = "err.msg.applnjar.empty";
    String KEY_I18N_ERR_PLTPROJ_EMPTY = "err.msg.pltproj.empty";
    
    
    String KEY_I18N_ERR_EMAIL_EMPTY = "err.msg.email.empty";
    String KEY_I18N_ERR_ADDRS_EMPTY	= "err.msg.addrs.empty";
    String KEY_I18N_ERR_ZIPCODE_EMPTY = "err.msg.zip.empty";
    String KEY_I18N_ERR_CONTNUM_EMPTY = "err.msg.contnum.empty";
    String KEY_I18N_ERR_FAXNUM_EMPTY = "err.msg.faxnum.empty";
    String KEY_I18N_ERR_COUN_EMPTY = "err.msg.country.empty";
    String KEY_I18N_ERR_LICEN_EMPTY = "err.msg.licence.empty";
    String KEY_I18N_ERR_URL_EMPTY = "err.msg.url.empty";
    String KEY_I18N_ERR_APPLNPLTF_EMPTY = "err.msg.applnpltf.empty";
    String KEY_I18N_ERR_GROUP_EMPTY = "err.msg.group.empty";
    String FEATURE_ADDED = "feature.add.success";
    String FEATURE_NOT_ADDED = "feature.add.fail";
    String APPLNTYPES_ADDED = "applntypes.add.success";
    String APPLNTYPES_NOT_ADDED = "applntypes.add.fail";
    String APPLNTYPES_UPDATED = "applntypes.update.success";
    String APPLNTYPES_NOT_UPDATED = "applntypes.update.fail";
    String APPLNTYPES_DELETED = "appTyep.delete.success";
    String APPLNTYPES_NOT_DELETED = "appType.delete.fail" ;
    String CONFIGTEMPLATE_ADDED = "congiftemplate.add.success";
    String CONFIGTEMPLATE_NOT_ADDED = "congiftemplate.add.fail";
    String ARCHETYPE_ADDED = "archetype.add.success";
    String ARCHETYPE_NOT_ADDED = "archetype.add.fail";
    String ARCHETYPE_UPDATED = "archetype.update.success";
    String ARCHETYPE_NOT_UPDATED = "archetype.update.fail";
    String ARCHETYPE_DELETED = "archetype.delete.success";
    String ARCHETYPE_NOT_DELETED = "archetype.delete.fail" ;
    String PLTPROJ_ADDED = "pltproj.add.success";
    String PLTPROJ_NOT_ADDED = "pltproj.add.fail";
    String CUSTOMER_ADDED = "customer.add.success";
    String CUSTOMER_UPDATED = "customer.update.success";
    String CUSTOMER_DELETED = "customer.delete.success";
    String ROLE_ADDED = "role.add.success";
    String URL_ADDED = "url.add.success";
    String DOWNLOAD_ADDED = "download.add.success";
    String CUSTOMER_NOT_ADDED = "customer.add.fail";
    String CUSTOMER_NOT_UPDATED = "customer.update.fail";
    String CUSTOMER_NOT_DELETED = "customer.delete.fail";
    String DOWNLOAD_NOT_ADDED = "download.add.fail";
    String ROLE_NOT_ADDED     = "role.add.fail";
    String URL_NOT_ADDED      = "url.add.fail";

}
