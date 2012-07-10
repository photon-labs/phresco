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
		List<Technology> techList = mongoOperation.find(TECHNOLOGIES_COLLECTION_NAME, 
		        new Query(Criteria.where(REST_API_FIELD_APPID).is(appTypeDAO.getId())), Technology.class);
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
