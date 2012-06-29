/*
 * ###
 * Service Web Archive
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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
package com.photon.phresco.service.rest.api;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.document.mongodb.MongoOperations;

import com.mongodb.MongoException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.ServiceConstants;

public class DbService {
	
	private static final Logger S_LOGGER= Logger.getLogger(DbService.class);
	private static final String MONGO_TEMPLATE = "mongoTemplate";
	protected MongoOperations mongoOperation;

	protected DbService() throws PhrescoException {
		S_LOGGER.debug("Entered Into DbService()");
		ApplicationContext ctx;
		try {
			ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
	    	mongoOperation = (MongoOperations)ctx.getBean(MONGO_TEMPLATE);
		} catch (MongoException e) {
			PhrescoException phrescoException = new PhrescoException(ServiceConstants.EX_PHEX00004);
			S_LOGGER.error("PhrescoException Is" + phrescoException.getErrorMessage());
			throw phrescoException;
		}
	}
}
