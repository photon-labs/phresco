package com.photon.phresco.service.rest.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.document.mongodb.MongoOperations;

public class DbService {

	private static final String MONGO_TEMPLATE = "mongoTemplate";
	protected MongoOperations mongoOperation;

	protected DbService() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
    	mongoOperation = (MongoOperations)ctx.getBean(MONGO_TEMPLATE);
	}
}
