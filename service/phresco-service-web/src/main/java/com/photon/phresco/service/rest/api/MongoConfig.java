package com.photon.phresco.service.rest.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public @Bean Mongo mongo() throws Exception {
		return new Mongo("localhost");
	}

	@Override
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "phresco" , "phrescoService");
	}

}
