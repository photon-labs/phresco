package com.photon.phresco.service.converters;

import java.util.List;

import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.Converter;
import com.photon.phresco.service.dao.ApplicationTypeDAO;
import com.photon.phresco.util.ServiceConstants;

public class ApplicationTypeConverter implements Converter<ApplicationTypeDAO, ApplicationType>, ServiceConstants {

	@Override
	public ApplicationType convertDAOToObject(ApplicationTypeDAO appTypeDAO,
			MongoOperations mongoOperation) throws PhrescoException {
		ApplicationType appType = new ApplicationType();
		appType.setId(appTypeDAO.getId());
		appType.setDescription(appTypeDAO.getDescription());
		appType.setName(appTypeDAO.getName());
		List<Technology> techList = mongoOperation.find(TECHNOLOGIES_COLLECTION_NAME, new Query(Criteria.where(REST_API_FIELD_APPID).is(appTypeDAO.getId())), Technology.class);
		appType.setTechnologies(techList);
		return appType;
	}

	@Override
	public ApplicationTypeDAO convertObjectToDAO(ApplicationType applicationType)
			throws PhrescoException {
		ApplicationTypeDAO appTypeDAO = new ApplicationTypeDAO();
		appTypeDAO.setId(applicationType.getId());
		appTypeDAO.setName(applicationType.getName());
		appTypeDAO.setDescription(applicationType.getDescription());
		appTypeDAO.setDisplayName(applicationType.getDisplayName());
		return appTypeDAO;
	}
	
}
