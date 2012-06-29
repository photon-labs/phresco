/**
 * 
 */
package com.photon.phresco.service.api;

import org.springframework.data.document.mongodb.MongoOperations;

import com.photon.phresco.commons.model.Element;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.dao.BaseDAO;

/**
 * @author kumar_s
 *
 */
public interface Converter<T extends BaseDAO, E extends Element> {
	
	E convertDAOToObject(T dao, MongoOperations mongoOperation) throws PhrescoException;
	
	T convertObjectToDAO(E element) throws PhrescoException;
	
}
