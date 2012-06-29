package com.photon.phresco.service.api;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
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
