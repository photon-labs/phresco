package com.photon.phresco.service.rest.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.service.model.ServerConstants;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration implements ServerConstants{
	
	private ServerConfiguration config;
	
	public MongoConfig() throws PhrescoException {
		PhrescoServerFactory.initialize();
		config = PhrescoServerFactory.getServerConfig();
	}
	
	@Override
	public @Bean Mongo mongo() throws Exception {
		return new Mongo(config.getDbHost(), config.getDbPort());
	}

	@Override
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), config.getDbName() , config.getDbCollection());
	}
}
