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

package com.photon.phresco.service.api;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.util.ServiceConstants;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration implements ServiceConstants{
	
	private ServerConfiguration config;
	
	public MongoConfig() throws PhrescoException {
		PhrescoServerFactory.initialize();
		config = PhrescoServerFactory.getServerConfig();
	}
	
	@Override
	public @Bean Mongo mongo() throws PhrescoException {
		Mongo mongo = null;
		try {
			mongo = new Mongo(config.getDbHost(), config.getDbPort());
		} catch (UnknownHostException e) {
			throw new PhrescoException(EX_PHEX00002);
		} catch (MongoException e) {
			throw new PhrescoException(EX_PHEX00003);
		}
		return mongo;
	}

	@Override
	public @Bean MongoTemplate mongoTemplate() throws PhrescoException {
		MongoTemplate mongoTemplate = null;
		try {
			mongoTemplate = new MongoTemplate(mongo(), config.getDbName() , config.getDbCollection());
		}catch (MongoException e) {
			throw new PhrescoException(EX_PHEX00003);
		}
		return mongoTemplate;
	}
}
